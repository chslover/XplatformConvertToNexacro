package com.convert.nexa;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class UI {

	Frame frame;
	Panel controlPanel;
	Label sourcelabel;
	TextArea sourceTextArea;
	Label destinationlabel;
	TextArea destinationTextArea;
	Button showButton;

	public void createFrame(RunConvert runConvert) {
		frame = new Frame("convert");

		frame.setSize(800,600);

		frame.setLayout(new GridLayout(3,1));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		controlPanel = new Panel();
		controlPanel.setLayout(new FlowLayout());

		frame.add(controlPanel);

		sourcelabel = new Label("convert Src : ", Label.RIGHT);
        sourceTextArea = new TextArea(
                "",
                5, 30);


        destinationlabel = new Label("converted Src: ", Label.RIGHT);
        destinationTextArea = new TextArea(
                "",
                5, 30);

        showButton = new Button("converting..");
        showButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
					destinationTextArea.setText(runConvert.converting(new String(sourceTextArea.getText().getBytes("UTF-8"), "UTF-8")));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        });

        controlPanel.add(sourcelabel);
        controlPanel.add(sourceTextArea);
        controlPanel.add(destinationlabel);
        controlPanel.add(destinationTextArea);
        controlPanel.add(showButton);

        frame.setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//ConvertingJS convert = new ConvertingJS();
		UI ui = new UI();
		RunConvert convert = new RunConvert();
		ui.createFrame(convert);

	}
}
