package com.java.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.math.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
import org.apache.commons.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ParsePdf {

	public static void main(String[] args) throws IOException, InterruptedException {


	}

	public static void createExcel(String user,String company_name,String dir_path,HashMap<String,Double> cost_cl,HashMap<String,Double> cost_pp) throws IOException {
//		String dir_path = "C:/Users/pyogaraj/Documents/PDF";
		File dir = new File(dir_path);
		// int rowid =1;
		File[] li = dir.listFiles();
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the company name");
//		String company_name = s.nextLine();
//		System.out.println("Enter the Variable amt");
//		double var_amt = s.nextDouble();
//		System.out.println("Enter the material cost for SS,MS,AL");
//		double mat_amt = s.nextDouble();
//		FileOutputStream out = new FileOutputStream("C:/Users/pyogaraj/Desktop/laser.xlsx");
		FileOutputStream out = new FileOutputStream(dir_path+"/"+company_name+".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		Row row;
		Cell cell;
		wb.setSheetName(0, "Laser_output");
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("Sl.no");
		Cell cell1 = row.createCell(1);
		cell1.setCellValue("company");
		Cell cell2 = row.createCell(2);
		cell2.setCellValue("Prepared By");
		Cell cell3 = row.createCell(3);
		cell3.setCellValue("Material");
		Cell cell4 = row.createCell(4);
		cell4.setCellValue("QTY");
		Cell cell5 = row.createCell(5);
		cell5.setCellValue("Thickness");
		Cell cell6 = row.createCell(6);
		cell6.setCellValue("Piercing");
		Cell cell7 = row.createCell(7);
		cell7.setCellValue("Running Meter");
		Cell cell8 = row.createCell(8);
		cell8.setCellValue("COST");
		 Cell cell9 = row.createCell(9);
		 cell9.setCellValue("machine Time");
		 Cell cell10 = row.createCell(10);
		 cell10.setCellValue("Surface Area");
		Cell cell12 = row.createCell(11);
		cell12.setCellValue("File Name");
	
		if (li != null) {
			int rowid = 1;
			for (File name : li) {

				System.out.println("name is" + name);
				String pdf_name = String.valueOf(name);
				String[] values = get_material_name(pdf_name);
				String material = values[1];
				System.out.println("material return is" + material);
				float thickness = Float.parseFloat(values[2]);
				System.out.println("thickness return is" + thickness);
//				get_pdf_detail(wb, sheet, company_name, material, thickness, var_amt, mat_amt, pdf_name, rowid);
				get_pdf_detail(wb, sheet, company_name, material, thickness, cost_cl, cost_pp, pdf_name, rowid,user);
				rowid++;
			}
		}
		wb.write(out);
		out.close();
	}

	public static String[] get_material_name(String file_path) throws InvalidPasswordException, IOException {
		HashMap<String, String> material_type = new HashMap<String, String>();
		HashMap<String, Double> material_cost = new HashMap<String, Double>();
		String[] return_list = { "dummy", "", "" };
		material_type.put("1.4301", "Stainless Steel");
		material_type.put("St37", "Mild Steel");
		material_type.put("AlMg3", "Aluminium");

		material_cost.put("Stainless Steel", 10.0);
		material_cost.put("Mild Steel", 20.0);
		material_cost.put("Aluminium", 30.0);

		// String file_path = "C:/Users/pyogaraj/Documents/PDF/sample1.pdf";
		File f = new File(file_path);
		PDDocument document = PDDocument.load(f);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		String[] name = text.split("\n");
		String[] ref_array = { "MATERIAL ID (SHEET):" };

		for (int i = 0; i < name.length; i++) {
			for (String cmp : ref_array) {
				if (name[i].contains(cmp)) {
					System.out.println("material name is " + name[i]);
					String[] material = name[i].split(":");
					String[] material_second = material[1].split("\\(");
					System.out.println("material_second is" + material_second[0]);
					String[] material_final = material_second[0].split("-");
					System.out.println("material_final is" + material_final[0]);
					// System.out.println("hash"+material_type);
					String Material = material_type.get(material_final[0].replaceAll(" ", ""));
					System.out.println("material " + Material);
					float thickness = Integer.parseInt(material_final[1].replaceAll(" ", ""));
					thickness = thickness / 10;
					String thickness_str = String.valueOf(thickness);
					System.out.println("thickenss " + thickness);
					return_list[1] = Material;
					return_list[2] = thickness_str;
					// createExcel(Material,thickness);
				}

			}
		}
		return return_list;
	}

	public static void get_pdf_detail(XSSFWorkbook wb, XSSFSheet sheet, String company_name, String material,
			float thickness, HashMap<String,Double> cost_cl, HashMap<String,Double>cost_pp, String file_path, int rowid,String user)
			throws InvalidPasswordException, IOException {
//		System.out.println("rowid s" + rowid);
//		System.out.println("user name is"+user);
		// int rowid=1;
		Row row;
		Cell cell;
		double var_amt = cost_cl.get(material);
		double mat_amt = cost_pp.get(material);
		String[] ref_array = { "NUMBER:", "DIMENSIONS:", "NUMBER OF PIERCING POINTS:", "MACHINING TIME:",
				"CUTTING LENGTH:" ,"PIERCING TIME"};
		// String file_path = "C:/Users/pyogaraj/Documents/PDF/sample1.pdf";
		File f = new File(file_path);
		String fname = String.valueOf(f.getName());
		PDDocument document = PDDocument.load(f);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		String text = pdfStripper.getText(document);
		String[] name = text.split("\n");

		String pattern1 = "^NUMBER: (.*)";
		String pattern2 = "^NUMBER OF PIERCING POINTS: (.*)";
		Pattern comp1 = Pattern.compile(pattern1);
		Pattern comp2 = Pattern.compile(pattern2);
		Pattern comp3 = Pattern.compile("^MACHINING TIME:(.*)");
		Pattern comp4 = Pattern.compile("^CUTTING LENGTH: (.*)");
		Pattern comp5 = Pattern.compile("^PART NUMBER: (.*)");
		Pattern comp6 = Pattern.compile("^DIMENSIONS: (.*)");
		Pattern comp7 = Pattern.compile("^PIERCING TIME (.*)");

		ArrayList<Integer> tmp_number = new ArrayList<Integer>();
		ArrayList<Double> tmp_cutting_length = new ArrayList<Double>();
		ArrayList<Double> machining_time = new ArrayList<Double>();
		int counter = 0;
		for (int i = 0; i < name.length; i++) {
			int number = 0;

			String partnumber = null;
			String PN = null;
			String num = null;
			// String mach_time = null;
			// int piercing_pt = 0;
			float cutting_length = 0;

			for (String cmp : ref_array) {
				if (name[i].contains(cmp)) {
					Matcher m1 = comp1.matcher(name[i]);
					Matcher m2 = comp2.matcher(name[i]);
					Matcher m3 = comp3.matcher(name[i]);
					Matcher m4 = comp4.matcher(name[i]);
					Matcher m5 = comp5.matcher(name[i]);
					Matcher m6 = comp6.matcher(name[i]);
					Matcher m7 = comp7.matcher(name[i]);

					if (m5.find()) {
						PN = String.valueOf(m5.group(1));

					}
					if (m1.find()) {

						String number1 = m1.group(1).replaceAll(" ", "");
						number = Integer.parseInt(number1);
						if (!tmp_number.isEmpty()) {
							tmp_number.remove(0);
						}
						tmp_number.add(number);
						num = String.valueOf(number);

					}
					if (m6.find()) {

						row = sheet.createRow(rowid);
//						row = sheet.getRow(rowid);
						cell = row.createCell(0);
						cell.setCellValue(rowid);

						row = sheet.getRow(rowid);
						cell = row.createCell(1);
						cell.setCellValue(company_name);
						
						row = sheet.getRow(rowid);
						cell = row.createCell(2);
						cell.setCellValue(user);
						
						row = sheet.getRow(rowid);
						cell = row.createCell(3);
						cell.setCellValue(material);

						row = sheet.getRow(rowid);
						cell = row.createCell(5);
						cell.setCellValue(thickness);

						row = sheet.getRow(rowid);
						cell = row.createCell(4);
						cell.setCellValue(1);
						
						
						row = sheet.getRow(rowid);
						cell = row.createCell(11);
						System.out.println("file name is" + fname);
						cell.setCellValue(fname);
					

						// System.out.println("row id of surface"+rowid);
						System.out.println("Dimension is"+m6.group(0));
						String[] split_surface = m6.group(1).split(" ");
						double length = Double.parseDouble(split_surface[0]);
						System.out.println("length  is"+length);
						length = length/304.8;
						
//						String[] width = split_surface[1].split(" ");
						double width =Double.parseDouble(split_surface[2]);
						System.out.println("width is"+width);
						width = width/304.8;
						
						double surface = length * width;
						surface = surface*2;
						surface = surface *1;
						System.out.println("surface is "+surface);
						 row = sheet.getRow(rowid);
						 cell = row.createCell(10);
						try {
//							int surface = Integer.parseInt(split_surface[0]);
							 cell.setCellValue(surface);
						} catch (Exception e) {
							System.out.println("inside Dimensions catch");
//							float surface = Float.parseFloat(split_surface[0]);
							// System.out.println("Surfece is"+surface);
							 cell.setCellValue(surface);
						}
					
					}
					if (m3.find()) {
						 

						if (counter != 0) {
//							row = sheet.getRow(rowid);
//							cell = row.createCell(9);
							String str_mach_time = m3.group(1);
							str_mach_time = str_mach_time.replaceAll(" ", "");
							String[] time = str_mach_time.split("min");
							try {
//								float machine_time = Float.parseFloat(time[0]);
								double machine_time = Double.parseDouble(time[0]);
//								System.out.println("machine time"+machine_time);
								if(!machining_time.isEmpty()){
									machining_time.remove(0);
								}
								machining_time.add(machine_time);
								// cell.setCellValue(machine_time);
							} catch (Exception e) {
//								int machine_time = Integer.parseInt(time[0]);
								double machine_time = Double.parseDouble(time[0]);
								if(!machining_time.isEmpty()){
									machining_time.remove(0);
								}
								machining_time.add(machine_time);
								// cell.setCellValue(machine_time);
							}

						}
						counter++;
					}
					if (m4.find()) {
						// System.out.println("row id "+rowid);
						String[] CL1;
						String CL = m4.group(1);
						CL1 = CL.split(" ");
						row = sheet.getRow(rowid);
						cell = row.createCell(7);
						// System.out.println("Cutting length is"+CL1[0]);
						try {
							cutting_length = Integer.parseInt(CL1[0]);
							cutting_length = (cutting_length / 1000);
							cutting_length = cutting_length * tmp_number.get(0);
							int cutting_length_round = Math.round(cutting_length);
//							System.out.println("CUTTING LENGTH " + cutting_length);
							cell.setCellValue(cutting_length);
							double cost_cut_len = cutting_length * thickness;
							cost_cut_len = cost_cut_len * var_amt;
							if (!tmp_cutting_length.isEmpty()) {
								tmp_cutting_length.remove(0);
							}
							tmp_cutting_length.add(cost_cut_len);

						} catch (Exception e) {
							cutting_length = Float.parseFloat(CL1[0]);
							cutting_length = (cutting_length / 1000);
							cutting_length = cutting_length * tmp_number.get(0);
							int cutting_length_round = Math.round(cutting_length);
//							System.out.println("CUTTING LENGTH " + cutting_length);
							cell.setCellValue(cutting_length);
							double cost_cut_len = cutting_length * thickness;
							cost_cut_len = cost_cut_len * var_amt;
//							System.out.println("cost of CL " + cost_cut_len);
							if (!tmp_cutting_length.isEmpty()) {
								tmp_cutting_length.remove(0);
							}
							tmp_cutting_length.add(cost_cut_len);

						}
					}
					if(m7.find()){
						try{							
						String[] pierce_time = m7.group(0).split(" ");
						double piercing_time = Double.parseDouble(pierce_time[2]);
						row = sheet.getRow(rowid);
						cell = row.createCell(9);
						System.out.println("machining time is"+machining_time.get(0));
						System.out.println("inside pierce time "+piercing_time);
						System.out.println("number in pierce time is "+tmp_number.get(0));
						piercing_time = piercing_time/60;
						double final_mach_time = machining_time.get(0) + piercing_time;
						System.out.println("final_machine time is"+final_mach_time);
						final_mach_time  = 	final_mach_time * tmp_number.get(0);
						cell.setCellValue(final_mach_time);
						}
						catch(Exception e){
							System.out.println(e);
							System.out.println("inside machine catch");
						
							
						}
						rowid++;
					}

					if (m2.find()) {
						// System.out.println("tmp is"+tmp);
						// piercing_pt = Integer.parseInt(name[i]);
						String pp = m2.group(1).replaceAll(" ", "");
						int Pierce_Point = Integer.parseInt(pp);
						row = sheet.getRow(rowid);
						cell = row.createCell(6);
						// cell.setCellValue(m2.group(1));
						Pierce_Point = Pierce_Point * tmp_number.get(0);
						cell.setCellValue(Pierce_Point);
						System.out.println("pierce point is" + Pierce_Point);
						double cost_pierce_point = Pierce_Point * mat_amt;
						cost_pierce_point = cost_pierce_point * thickness;
						System.out.println("cost of pp " + cost_pierce_point);
						double total = cost_pierce_point + tmp_cutting_length.get(0);

						row = sheet.getRow(rowid);
						cell = row.createCell(8);
						cell.setCellValue(total);
//						rowid++;
						// System.out.println("NUMBER OF PIERCING POINTS " +
						// m2.group(1));
					}

				}

			}

		}
		// System.out.println("text is "+text);
		// Closing the document
		// rowid++;
		System.out.println("outside rowid is" + rowid);
		document.close();

	}

}
