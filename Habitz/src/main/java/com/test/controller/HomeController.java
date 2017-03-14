package com.test.controller;
import org.hibernate.*;


import com.test.models.TasksEntity;
import com.test.models.UsernamesEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

import java.util.Map;

@Controller
public class HomeController {

//    @RequestMapping("listCustomers")public ModelAndView listCustomer(){
//        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
//    SessionFactory sessionFact = cfg.buildSessionFactory();
//    Session selectusers = sessionFact.openSession();
//        selectusers.beginTransaction();
//        Criteria c = selectusers.createCriteria(UsernamesEntity.class);
//        ArrayList<UsernamesEntity> customerList=(ArrayList<UsernamesEntity>)c.list();
//        return new ModelAndView("mainPage","cList",customerList);
//    }
    @RequestMapping("/")

    public ModelAndView landingPage() {
        FBConnection fbConnection = new FBConnection();


        return new
                ModelAndView("landing", "message", fbConnection.getFBAuthUrl());

    }


    @RequestMapping("mainPage")

    public ModelAndView welcome(@RequestParam("code") String code,Model model) {

        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        String out = "";
        String id = fbProfileData.get("id");
        out = fbProfileData.get("name");
        String email = fbProfileData.get("email");
//    ********* array list of users**************
        Criteria c = userNamelist();
        c.add(Restrictions.like("userid", "%" + out + "%"));
        ArrayList<UsernamesEntity> userList = (ArrayList<UsernamesEntity>) c.list();

//  *********** add user to database if not already there ******
        if (userList.size() == 0) {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            SessionFactory sessionFact = cfg.buildSessionFactory();
            Session session = sessionFact.openSession();
            Transaction tx = session.beginTransaction();
            UsernamesEntity newuser = new UsernamesEntity();
            newuser.setUserid(id);
            newuser.setFullname(out);
            newuser.setEmail(email);
            newuser.setPoints("0");
            session.save(newuser);
            tx.commit();
            session.close();
        }
//   ******* Table of tasks *********
//        Criteria t = tasks();
//        ArrayList<TasksEntity> taskList = (ArrayList<TasksEntity>) t.list();
//        model.addAttribute("task", taskList);

        // Todo add friends

        return new
                ModelAndView("mainPage", "message", out);

    }


    private Criteria userNamelist() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectUsers = sessionFact.openSession();
        selectUsers.beginTransaction();
        return selectUsers.createCriteria(UsernamesEntity.class);
    }
    private Criteria tasks() {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFact = cfg.buildSessionFactory();
        Session selectTask = sessionFact.openSession();
        selectTask.beginTransaction();
        return selectTask.createCriteria(TasksEntity.class);
    }


    @RequestMapping("leaderBoard")

    public ModelAndView leading(@RequestParam("code") String code) {
        if (code == null || code.equals("")) {
            throw new RuntimeException(
                    "ERROR:Didn't get code parameter in callback.");
        }
        FBConnection fbConnection = new FBConnection();
        String accessToken = fbConnection.getAccessToken(code);

        FBGraph fbGraph = new FBGraph(accessToken);
        String graph = fbGraph.getFBGraph();
        Map<String, String> fbProfileData = fbGraph.getGraphData(graph);
        String out = "";
        out = out.concat("<div>Welcome " + fbProfileData.get("name"));

        // todo add points for all friends of this user

        return new
                ModelAndView("leaderBoard", "message", out);

    }

}
