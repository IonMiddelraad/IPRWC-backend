package com.IonMiddelraad.iprwcbackend.security;

import com.IonMiddelraad.iprwcbackend.Permission;
import com.IonMiddelraad.iprwcbackend.dao.UserDAO;
import com.IonMiddelraad.iprwcbackend.model.Role;
import com.IonMiddelraad.iprwcbackend.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class RoleFilter extends OncePerRequestFilter {

    private final UserDAO userDAO;

    private final LinkedHashMap<String, String[]> routePermissions;

    public RoleFilter(UserDAO employeeDAO) {
        this.userDAO = employeeDAO;
        this.routePermissions = new LinkedHashMap<String, String[]>();

        this.routePermissions.put("GET /api/user/info",
                new String[]{
                        String.valueOf(Permission.AUTHENTICATE)});
        this.routePermissions.put("PUT /api/user/*",
                new String[]{
                        String.valueOf(Permission.ADMIN)});
        this.routePermissions.put("GET /api/user/role",
                new String[]{
                        String.valueOf(Permission.ADMIN)});
        this.routePermissions.put("GET /api/user/role/*",
                new String[]{
                        String.valueOf(Permission.ADMIN)});
        this.routePermissions.put("POST /api/user/role",
                new String[]{
                        String.valueOf(Permission.ILLEGAL)});
        this.routePermissions.put("PUT /api/user/role/*",
                new String[]{
                        String.valueOf(Permission.ILLEGAL)});
        this.routePermissions.put("DELETE /api/user/role/*",
                new String[]{
                        String.valueOf(Permission.ILLEGAL)});
        this.routePermissions.put("GET /api/user/product/all",
                new String[]{
                        String.valueOf(Permission.AUTHENTICATE)});
        this.routePermissions.put("GET /api/user/product/*",
                new String[]{
                        String.valueOf(Permission.AUTHENTICATE)});
        this.routePermissions.put("PUT /api/user/product/*",
                new String[]{
                        String.valueOf(Permission.ADMIN)});
    }

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String key = request.getMethod() + " " + request.getRequestURI();

        List<Integer> pathVariables = new ArrayList<>();
        for(String value: key.split("/")) {
            try{
                pathVariables.add(Integer.parseInt(value));
            } catch (NumberFormatException e) {
            }
        }

        key = key.replaceAll("\\d+","*");

        boolean access = true;

        if(routePermissions.containsKey(key)) {

            // Getting all the user permissions
            User user = userDAO.getEmployeeDetails();
            String[] needed = routePermissions.get(key);
            List<String> userPermissions = new ArrayList<>();

            for(Role role: user.getRoles()) {
                userPermissions.addAll(Arrays.asList(role.getPermissions()));
            }

            String method = request.getMethod();
//            if((Objects.equals(method, "DELETE") || Objects.equals(method, "PUT"))
//                    && !Arrays.asList(needed).contains(String.valueOf(Permission.ADMIN))) {
//
//                //Getting the meetingroomReservations and workroomReservations of the user
//                List<MeetingRoomReservation> userMeetingRoomReservations = this.meetingRoomReservationDAO.userMeetingRooms(employee.getId());
//                List<WorkRoomReservation> userWorkRoomReservations = this.workRoomReservationDAO.userWorkRooms(employee.getId());
//
//                switch (method) {
//                    case "DELETE" -> {
//                        if (userPermissions.contains(String.valueOf(Permission.RESERVATION_DELETE_OTHER))) {
//                            access = true;
//                        }
//                        int idIndex = pathVariables.size() - 1;
//                        access = canAccess(request, access, userMeetingRoomReservations, userWorkRoomReservations, idIndex);
//                    }
//                    case "PUT" -> {
//                        if (userPermissions.contains(String.valueOf(Permission.RESERVATION_UPDATE_OTHER))) {
//                            access = true;
//                        }
//                        int idIndex = pathVariables.size() - 1;
//                        access = canAccess(request, access, userMeetingRoomReservations, userWorkRoomReservations, idIndex);
//                    }
//                    default -> {
//                    }
//                }
//            } else {
//                access = true;
//            }

            // Check if access has changed to false after the switch statement
            if(!access) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        String.format("User is unauthorized to use %s on endpoint %s&n", request.getMethod(), request.getRequestURI())
                );
            }

            // Check if the user has the permissions
            for(String permission: needed) {
                if(userPermissions.contains(permission)) {
                    access = true;
                } else {
                    access = false;
                    break;
                }
            }

            // Send 403 if user doesn't have all the required permissions
            if(!access) {
                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        String.format("User is unauthorized to use %s on endpoint %s&n", request.getMethod(), request.getRequestURI())
                );
            }
        }

        filterChain.doFilter(request, response);
    }

}
