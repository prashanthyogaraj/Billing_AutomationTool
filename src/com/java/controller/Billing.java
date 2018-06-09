package com.java.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.java.main.*;
/**
 * Servlet implementation class Billing
 */
@WebServlet("/Billing")
public class Billing extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Billing() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside servlet");
		ParsePdf pdf =  new ParsePdf();
		HashMap<String,Double> cost_cl = new HashMap<String,Double>();
		HashMap<String,Double> cost_pp = new HashMap<String,Double>();
		String uploader = request.getParameter("uploader");
		String company = request.getParameter("company");
		String path = request.getParameter("path");
//		String cl_al = request.getParameter("cl_al");
		double cl_al  = Double.parseDouble(request.getParameter("cl_Al"));
		double cl_ms  = Double.parseDouble(request.getParameter("cl_ms"));
		double cl_ss  = Double.parseDouble(request.getParameter("cl_ss"));
		double pp_al  = Double.parseDouble(request.getParameter("pp_Al"));
		double pp_ms  = Double.parseDouble(request.getParameter("pp_ms"));
		double pp_ss  = Double.parseDouble(request.getParameter("pp_ss"));
		
//		double cutlen_al = Double.parseDouble(cl_al);
//		System.out.println("cuttinglen"+cutlen_al);
		System.out.println(uploader);
		System.out.println(path);
		
		cost_cl.put("Aluminium", cl_al);
		cost_cl.put("Mild Steel", cl_ms);
		cost_cl.put("Stainless Steel", cl_ss);
		
		cost_pp.put("Aluminium", pp_al);
		cost_pp.put("Mild Steel", pp_ms);
		cost_pp.put("Stainless Steel", pp_ss);
		
		System.out.println("cl hash is"+cost_cl);
		System.out.println("pp hash is"+cost_pp);
		pdf.createExcel(uploader,company,path, cost_cl, cost_pp);
		response.sendRedirect("landing.jsp");
		
//		request.getRequestDispatcher("/landing.jsp").forward(request, response);
	}

}
