package com.convert.nexa;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunConvert {
	InputStream is;
    BufferedReader br;
    StringBuilder stringBuilder;
    StringBuffer sb;
    List<Integer> position;

    List<String> thisList;
    List<String> applicationList;
    List<String> nexacroList;
    List<String> propertyList;
    List<String> formVarList;
    RunConvert (){
    	thisList = new ArrayList<>();
    	applicationList = new ArrayList<>();
    	nexacroList = new ArrayList<>();
    	propertyList = new ArrayList<>();
    	formVarList = new ArrayList<>();
		// btn, cal, stc, edit, medit, AttachFiles 占쏙옙..占쏙옙占쏙옙트占쏙옙占쏙옙占�..

		//org comp
//		thisList.add("Div");
//		thisList.add("Button");
//		thisList.add("PopupDiv");
//		thisList.add("Combo");
//		thisList.add("CheckBox");
//		thisList.add("ListBox");
//		thisList.add("Edit");
//		thisList.add("MaskEdit");
//		thisList.add("TextArea");
//		thisList.add("Menu");
//		thisList.add("Tab");
//		thisList.add("ImageViewer");
//		thisList.add("Radio");
//		thisList.add("Calendar");
//		thisList.add("Static");
//		thisList.add("Grid");
//		thisList.add("Spin");
//		thisList.add("PopupMenu");
//		thisList.add("GroupBox");
//		thisList.add("ProgressBar");
//		thisList.add("Plugin");
		//thisList.add("Dataset");

		//mod comp
		thisList.add("btn");
		thisList.add("pdivv");
		thisList.add("cmb");
		thisList.add("chk");
		thisList.add("lbox");
		thisList.add("edt");
		thisList.add("edit");
		thisList.add("medit");
		thisList.add("menu");
		thisList.add("tab");
		thisList.add("img");
		thisList.add("rb");
		thisList.add("cal");
		thisList.add("stc");
		thisList.add("grid");
		thisList.add("spin");
		thisList.add("pmenu");
		thisList.add("gb");
		thisList.add("pbar");

		//thisList.add("ta");

		/////////////////////////
		thisList.add("transaction");
		thisList.add("cfn");
		thisList.add("callbackFunction");


		/////////////////////////
		nexacroList.add("wrapQuote");
		nexacroList.add("toNumber");

		/////////////////////////
		propertyList.add(".text");
		propertyList.add(".value");
		propertyList.add(".readonly");
		propertyList.add(".enable");
		propertyList.add(".visible");
		propertyList.add(".enableevent");
		propertyList.add(".updatecontrol");
		propertyList.add(".tabindex");
		propertyList.add(".rowposition");
		
		 
		//applicationList.add("gds");
    }
	private void clearData() {
		sb.setLength(0);
		position.clear();
	}

	/************* Read file ************/
	public String converting(File file) throws IOException {
		return converting("");
	}
	public String converting(String allSource) throws IOException {
		/************* Source / Script Substring ************/
		String script, firstSource, lastSource;
		int sourceIndexOfValue = allSource.indexOf("CDATA");
		if(sourceIndexOfValue > -1) {
			script = allSource.substring(allSource.indexOf("CDATA[") + "CDATA[".length(), allSource.indexOf("]]"));
			firstSource = allSource.substring(0, allSource.indexOf("CDATA[") + "CDATA[".length());
			lastSource = allSource.substring(allSource.indexOf("]]"), allSource.length());
		} else {
			script = allSource;			
			firstSource = "";
			lastSource = "";
		}
		/************* Init ************/
		is = new ByteArrayInputStream(script.getBytes());
		br = new BufferedReader(new InputStreamReader(is));
		stringBuilder = new StringBuilder();
		sb = new StringBuffer();
		position = new ArrayList<Integer>();

		
	
		
		/************* Source TAB Convert ************/
		//System.out.println(firstSource);
		//System.out.println(lastSource);
		
		/************* Script TAB Convert ************/
		String convertedStr = "";
        String line = "";
        String scope = "this.";
        String appliScope = "application.";
        String nexa = "nexacro.";
        String func = "function";
        boolean isNotFoundFirstFunctioned = true;
                		
        while((line = br.readLine()) != null) {
        	if(line.contains("Script::")){
        		if(line.indexOf(".js") > -1) {
	        		clearData();
	        		sb.append(line);
	        		sb.insert(line.indexOf(".js") +1, "x");
	        		 
	        		line = sb.toString();
        		}
        	}
        	if(isNotFoundFirstFunctioned){
        		if(line.contains("var")){
        			String tempLine;
        			int varNameLastLength = line.indexOf("=") > -1 ? line.indexOf("=") : line.indexOf(";"); //변수 초기화이거나 아니거나.
        			tempLine = line.substring(line.indexOf("var") + "var ".length(), varNameLastLength);
        			
        			tempLine = tempLine.replaceAll(" ", "");
        			formVarList.add(tempLine);
        			
        			//System.out.println(tempLine); 
        		}
        	} 
        	
        	if(line.contains("function")){
        		isNotFoundFirstFunctioned = false;
        		
        		String tempLine;
        		
        		tempLine = line;
        		tempLine = tempLine.replaceAll("\\s", "");
        		
        		//function 정의인 지 체크.
        		if(tempLine.indexOf("function") == 0 ) { 
        			line = tempLine;
	        		if("func".equals(line.replaceAll("\\t", "").substring(0,4))){
	        			line = line.replaceAll("\\t", "");
	
	        			line = line.substring(func.length(), line.length());
	
		        		line = line.substring(0,line.indexOf("(")) + " = " + func + line.substring(line.indexOf("("),line.length());
	
		        		line = scope + line;
	        		}
        		}
        	}
        	if(line.contains("fn")) {
        		if(!line.contains(".fn") && !line.contains("sfn")) { // 표占쏙옙占쏙옙占쏙옙占쏙옙 占쌕꿔보占쏙옙.
        			clearData();

	        		int pos = line.indexOf("fn");
	        		while(pos > -1){
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
        	if(line.contains("gds")) {
        		if(!line.contains("=gds") && !line.contains("\"gds")){
        			clearData();

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
        	/****
        	 *  btn, cal, stc, edit, medit, AttachFiles 
        	 *  btn.xxx
        	 *  this.btn.xx
        	 ****/
        	for(int k=0; k<thisList.size();k++){
        		if(line.contains(thisList.get(k))){
        			clearData();

        			int pos = line.indexOf(thisList.get(k));


        			while(pos > 0){
    					String substr = line.substring(pos-1, pos);
        				if(!"'".equals(substr) && !".".equals(substr) && !"\"".equals(substr) && !"_".equals(substr)) {
        					position.add(pos);
        				}
            			pos = line.indexOf(thisList.get(k), pos + 1);
            		}
            		sb.append(line);
            		for(int i = position.size()-1; i >= 0; i--){
            			sb.insert(position.get(i), scope);
            		}

            		line = sb.toString();
        		}
        	}
        	if(line.contains("ds")){
        		if(!line.contains("\"ds") && !line.contains("\" ds")){
	    			clearData();

	    			int pos = line.indexOf("ds");
	        		while(pos > 0){

	        			//if(pos > 0) {
    					String substr = line.substring(pos-1, pos);
        				if(!".".equals(substr) && !"_".equals(substr) && !"g".equals(substr) && !"=".equals(substr)) {
        					position.add(pos);
        				}
	    				//}
	        			pos = line.indexOf("ds", pos + 1);
	        		}
	        		sb.append(line);
	        		for(int i = position.size()-1; i >= 0; i--){
	        			sb.insert(position.get(i), scope);
	        		}

	        		line = sb.toString();
        		}
    		}

        	if(line.contains("sfn")) {
        		if(!line.contains(".sfn")) { // 표占쏙옙占쏙옙占쏙옙占쏙옙 占쌕꿔보占쏙옙.
        			clearData();

	        		int pos = line.indexOf("sfn");
	        		while(pos > -1){
	        			//System.out.println(pos);
	        			if(pos == 0) {
	        				position.add(pos);
	        				pos = line.indexOf("sfn", pos + 1);
	        			}
	        			else if(pos != 0 && line.charAt(pos-1) != 'c') {//占십울옙占쏙옙占�.
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
        	for(int k=0; k<nexacroList.size();k++){
	        	if(line.contains(nexacroList.get(k))) {
	        		if(!line.contains("=gds") && !line.contains("\"gds")){
	        			clearData();

	            		int pos = line.indexOf(nexacroList.get(k));
	            		while(pos > -1){
	            			position.add(pos);
	            			pos = line.indexOf(nexacroList.get(k), pos + 1);
	            		}
	            		sb.append(line);
	            		for(int i = position.size()-1; i >= 0; i--){
	            			sb.insert(position.get(i), nexa);
	            		}

	            		line = sb.toString();
	        		}
	        	}
        	}
        	/****
        	 *  property set 방식 변경
        	 *  .text, .value, .readonly, .enable, .visible, .enableevent
        	 *  as-is : .text = "";
        	 *  to-be : .set_text("");
        	 ****/
        	//공백지우고, = 있으면 변경 
        	for(int k=0; k<propertyList.size();k++){
	        	if(line.contains(propertyList.get(k))) {
	        		//System.out.println(line);
	        		clearData();
	        		
	        		//별도로 받아서 공백 지우고 확인 후 처리
	        		String tempLine;
	        		
	        		tempLine = line;
	        		
	        		tempLine = tempLine.replaceAll(" ", "");

	        		int openParenthesesPosition = propertyList.get(k).length() + tempLine.indexOf(propertyList.get(k));
	        		int closeParenthesesPosition = tempLine.indexOf(";");
	        		
	        		//TODO this.chkEffexYn.set_enable(this.DEALER_ID == "990001" ? false : true); 오류..
	        		if('=' == tempLine.charAt(openParenthesesPosition) && '=' != tempLine.charAt(openParenthesesPosition + 1)){
	        			line = line.replaceAll(" ", "");

	        			line = line.replace(line.charAt(openParenthesesPosition), '(');
	        			line = line.replace(line.charAt(closeParenthesesPosition), ')');
	        			
	        			line = line + ";";

	        			sb.append(line);
	        			
	        			// 변환 되었는지 체크..enable,enableevent와 같이 명칭 중복되는 프로퍼티때문..
	        			if('_' != line.charAt(line.indexOf(propertyList.get(k)) -1)){
	        				sb.insert(line.indexOf(propertyList.get(k)) +1, "set_");	        			
	        			}
	        			
	        			line = sb.toString();
	        		}
	        		
	        	}
        	}
        

        	/****
        	 * TODO <Source> 占싸븝옙 占쌓몌옙占쏙옙 expr 占쌉쇽옙 占쏙옙占싸븝옙 占쏙옙占쏙옙
        	 * as-is : expr:fn
			 * to-be : expr:comp.parent.fn
        	 ****/

        	/****
        	 * TODO eval 占쏙옙환
        	 * as-is : eval("fn_test("+param1+")");
        	 * to-be : eval("this.fn_test())").call(this, param1);
        	 ****/


        	stringBuilder.append(line);
        	stringBuilder.append("\n");
        }

        convertedStr = stringBuilder.toString();
        is.close();
        br.close();

        System.out.println("중간 완료");

        //다시 초기화 하여 사용..
        stringBuilder.setLength(0);
        is = new ByteArrayInputStream(convertedStr.getBytes());
		br = new BufferedReader(new InputStreamReader(is));
		
		/****
    	 *  전역변수 this 붙이기.
    	 *  as-is : var test = 0; test = 1;
    	 *  to-be : var test = 0; this.test = 1;
    	 ****/
		while((line = br.readLine()) != null) {			
			for(int i=0; i < formVarList.size(); i++){
				if(line.contains(formVarList.get(i))){
					if(line.indexOf(formVarList.get(i)) >= 4){						
						if(false == "var".equals(line.substring(line.indexOf(formVarList.get(i)) - 4,line.indexOf(formVarList.get(i))-1))){
							clearData();
							
							int pos = line.indexOf(formVarList.get(i));
							while(pos > -1){
								position.add(pos);
								pos = line.indexOf(formVarList.get(i), pos + 1);
							}
							sb.append(line);
							for(int k = position.size()-1; k >= 0; k--){
								sb.insert(position.get(k), scope);
							}
							
							line = sb.toString();
						} 
					} else {
						clearData();
						
						int pos = line.indexOf(formVarList.get(i));
						while(pos > -1){
							position.add(pos);
							pos = line.indexOf(formVarList.get(i), pos + 1);
						}
						sb.append(line);
						for(int k = position.size()-1; k >= 0; k--){
							sb.insert(position.get(k), scope);
						}
						
						line = sb.toString();
					}
				} 
			}
			stringBuilder.append(line);
        	stringBuilder.append("\n");
		}
		convertedStr = firstSource +  stringBuilder.toString() + lastSource;			

		is.close();
		br.close();
        
        System.out.println("완료");

        return convertedStr;
        
	}


}
