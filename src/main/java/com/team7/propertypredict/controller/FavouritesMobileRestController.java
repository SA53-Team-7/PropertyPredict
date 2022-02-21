package com.team7.propertypredict.controller;

import com.team7.propertypredict.model.Project;
import com.team7.propertypredict.model.User;
import com.team7.propertypredict.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mobile/favourites")
public class FavouritesMobileRestController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/list/{id}")
    public List<Project> getAllSavedProjects(@PathVariable("id") int id) {
        return projectService.findAllShortlistProjects(id);
    }

    @PostMapping("/check")
    public Map<String, Integer> isSaved(@RequestBody UserAndProject userAndProject) {
        Map<String, Integer> response = new HashMap<>();
        int check = projectService.checkIfShortlisted(userAndProject.getProject().getProjectId(), userAndProject.getUser());
        response.put("isSaved", check);
        return response;
    }

    @PostMapping("/save")
    public Map<String, Integer> update(@RequestBody UserAndProject userAndProject) {
        Map<String, Integer> response = new HashMap<>();

        projectService.updateShortlistedProject(userAndProject.getProject().getProjectId(), userAndProject.getUser().getUserId());
        int check = projectService.checkIfShortlisted(userAndProject.getProject().getProjectId(), userAndProject.getUser());
        response.put("success", check);

        return response;
    }

    static class UserAndProject {
        private User user;
        private Project project;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Project getProject() {
            return project;
        }

        public void setProject(Project project) {
            this.project = project;
        }
    }


}


