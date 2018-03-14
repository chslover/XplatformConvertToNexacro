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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunConvertFromFile {

	Frame frame;
	Panel controlPanel;

	public void createFrame() {
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

		Label sourcelabel = new Label("변환할 소스: ", Label.RIGHT);
        final TextArea sourceTextArea = new TextArea(
                "",
                5, 30);


        Label destinationlabel = new Label("변환된 소스: ", Label.RIGHT);
        final TextArea destinationTextArea = new TextArea(
                "",
                5, 30);

        Button showButton = new Button("변환");
        showButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		try {
					destinationTextArea.setText(converting(sourceTextArea.getText()));
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

	public String converting(String str) throws IOException {
		InputStream is = new ByteArrayInputStream(str.getBytes());


		String convertedStr = "";
        String line = "";
        String scope = "this.";
        String appliScope = "application.";
        String nexa = "nexacro.";
        String func = "function";

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();

        StringBuffer sb = new StringBuffer();
        List<Integer> position = new ArrayList<Integer>();

        while((line = br.readLine()) != null) {
//        	Pattern p = Pattern.compile("(^[function]*)");

//        	Matcher m = p.matcher(line);

//        	if(m.find()){
//        		System.out.println(line + "     ; 함수 정의 line");
//        	}


        	//bw.write(description);


        	if(line.contains("function")){
        		line = line.replaceAll("\\s", "");

        		//System.out.println(line);
        		//System.out.println("자른 거 : " + line.replaceAll("\\t", "").substring(0,4));
        		if("func".equals(line.replaceAll("\\t", "").substring(0,4))){
        			line = line.replaceAll("\\t", "");

        			line = line.substring(func.length(), line.length());

	        		line = line.substring(0,line.indexOf("(")) + " = " + func + line.substring(line.indexOf("("),line.length());

	        		line = scope + line;
        		}
        	}
        	if(line.contains("transaction")) {
        		sb.setLength(0);
        		position.clear();

        		int pos = line.indexOf("transaction");
        		while(pos > -1){
        			position.add(pos);
        			pos = line.indexOf("transaction", pos + 1);
        		}
        		sb.append(line);
        		for(int i = position.size()-1; i >= 0; i--){
        			sb.insert(position.get(i), scope);
        		}

        		line = sb.toString();
        	}
//        	if(line.contains("callbackFunction")) {
//        		sb.setLength(0);
//        		position.clear();
//
//        		int pos = line.indexOf("callbackFunction");
//        		while(pos > -1){
//        			position.add(pos);
//        			pos = line.indexOf("callbackFunction", pos + 1);
//        		}
//        		sb.append(line);
//        		for(int i = position.size()-1; i >= 0; i--){
//        			sb.insert(position.get(i), scope);
//        		}
//
//        		line = sb.toString();
//        	}

        	if(line.contains("wrapQuote")) {
        		sb.setLength(0);
        		position.clear();

        		int pos = line.indexOf("wrapQuote");
        		while(pos > -1){
        			position.add(pos);
        			pos = line.indexOf("wrapQuote", pos + 1);
        		}
        		sb.append(line);
        		for(int i = position.size()-1; i >= 0; i--){
        			sb.insert(position.get(i), nexa);
        		}

        		line = sb.toString();
        	}
        	if(line.contains("gds")) {
        		if(!line.contains("=gds") && !line.contains("\"gds")){
        			sb.setLength(0);
            		position.clear();

            		int pos = line.indexOf("gds");
            		while(pos > -1){
            			position.add(pos);
            			pos = line.indexOf("gds", pos + 1);
            		}
            		sb.append(line);
            		for(int i = position.size()-1; i >= 0; i--){
            			sb.insert(position.get(i), appliScope);
            		}

            		line = sb.toString();
        		}
        	}
        	if(line.contains("ds")) {
        		if(!line.contains("=ds") && !line.contains("\"ds") && !line.contains(".ds") && !line.contains("gds")){ // 표현식..
        			sb.setLength(0);
            		position.clear();

            		int pos = line.indexOf("ds");
            		while(pos > -1){
            			position.add(pos);
            			pos = line.indexOf("ds", pos + 1);
            		}
            		sb.append(line);
            		for(int i = position.size()-1; i >= 0; i--){
            			sb.insert(position.get(i), scope);
            		}

            		line = sb.toString();
        		}
        	}
        	if(line.contains("cfn")) {
        		sb.setLength(0);
        		position.clear();

        		int pos = line.indexOf("cfn");
        		while(pos > -1){
        			position.add(pos);
        			pos = line.indexOf("cfn", pos + 1);
        		}
        		sb.append(line);
        		for(int i = position.size()-1; i >= 0; i--){
        			sb.insert(position.get(i), scope);
        		}

        		line = sb.toString();
        	}
        	if(line.contains("fn")) {
        		if(!line.contains(".fn") && !line.contains("sfn")) { // 표현식으로 바꿔보기.
	        		sb.setLength(0);
	        		position.clear();

	        		int pos = line.indexOf("fn");
	        		while(pos > -1){
	        			//System.out.println(pos);
	        			if(pos == 0) {
	        				position.add(pos);
	        				pos = line.indexOf("fn", pos + 1);
	        			}
	        			else if(pos != 0 && line.charAt(pos-1) != 'c') {
	        				position.add(pos);
	        				pos = line.indexOf("fn", pos + 1);
	        			} else {
	        				pos = line.indexOf("fn", pos + 1);
	        			}
	        		}
	        		sb.append(line);
	        		for(int i = position.size()-1; i >= 0; i--){
	        			sb.insert(position.get(i), scope);
	        		}

	        		line = sb.toString();
        		}

        	}
        	if(line.contains("sfn")) {
        		if(!line.contains(".sfn")) { // 표현식으로 바꿔보기.
        			//System.out.println("sfn : " + line);
	        		sb.setLength(0);
	        		position.clear();

	        		int pos = line.indexOf("sfn");
	        		while(pos > -1){
	        			//System.out.println(pos);
	        			if(pos == 0) {
	        				position.add(pos);
	        				pos = line.indexOf("sfn", pos + 1);
	        			}
	        			else if(pos != 0 && line.charAt(pos-1) != 'c') {//필요없음.
	        				position.add(pos);
	        				pos = line.indexOf("sfn", pos + 1);
	        			} else {
	        				pos = line.indexOf("sfn", pos + 1);
	        			}
	        		}
	        		sb.append(line);
	        		for(int i = position.size()-1; i >= 0; i--){
	        			sb.insert(position.get(i), scope);
	        		}

	        		line = sb.toString();
        		}
        	}
        	// TODO .text = ""; --> set_text("");, .text; 대상 아님.
        	// .text, .value, .readonly, .enable, .visible, .enableevent
        	// TODO 변수 앞에 scope 추가. 지역변수제외..
        	// 변수 선언 체크하여 배열에 저장.
        	//
        	// TODO 컴포넌트 id앞에 scope 추가.
        	// btn, cal, stc, edit, medit, AttachFiles
        	stringBuilder.append(line);
        	stringBuilder.append("\n");
        }

        convertedStr = stringBuilder.toString();
        br.close();

        System.out.println("완료");

        return convertedStr;
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//ConvertingJS convert = new ConvertingJS();
		RunConvertFromFile convertFrame = new RunConvertFromFile();
		convertFrame.createFrame();

	}

}
