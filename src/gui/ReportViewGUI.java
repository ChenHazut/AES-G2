package gui;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import logic.ExamReport;

public class ReportViewGUI 
{
	// Labels:
	@FXML
	Label repID;
	@FXML
	Label avgLabel;
	@FXML
	Label midLabel;
	@FXML
	BarChart<String,Integer> histograma;

	public void initData(ExamReport report)
	{
		final CategoryAxis xAxis = new CategoryAxis(); // X axis is with the grades categories.
	    final NumberAxis yAxis = new NumberAxis(); // Y axis is with the numbers of grades for each category.

		XYChart.Series<String,Integer> series1 = new XYChart.Series<String,Integer>();
		for(int i=0; i<report.getPercentages().length; i++) {
			series1.getData().add(new XYChart.Data(report.getPercentages()[i], 
					report.headers[i]));
			
		}
        
		histograma.getData().addAll(series1);
	    histograma.setTitle("Grades histogram");
	    xAxis.setLabel("Grades");       
	    yAxis.setLabel("Number of exams");
	        
	}
	
}
