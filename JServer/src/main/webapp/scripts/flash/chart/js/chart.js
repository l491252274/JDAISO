function initPie(targetFrameObject, xSize, ySize, title, label, value)
{
	var total=0;
	var values = value.split(',');
	for(var i=0;i<values.length;i++)
		total=total+parseInt(values[i]);
	value = values[0]
	for(var i=1;i<values.length;i++)
		value=value+','+values[i];

	document.getElementById(targetFrameObject).innerHTML="<div id='pie_chart_" + targetFrameObject + "' style='padding: 0px; margin:10px; border: 1px solid lightblue; width: " + xSize + "px; height: " + ySize + "px;'></div>";
	var so = new SWFObject("/uoj/scripts/flash/chart/actionscript/open-flash-chart.swf", "ofc", xSize, ySize, "9", "#FFFFFF");
	title = title + ",{font-size:18px; color:#5284C7;}";
	so.addVariable("variables","true");
	so.addVariable("pie","60,#505050,{font-size: 12px; color: #404040;}");
	so.addVariable("title",title);
	so.addVariable("values",value);
	so.addVariable("pie_labels",label);
	so.addVariable("tool_tip","#x_label#<br>占百分比: #val#%25");
	so.addVariable("colours","d01f3c,356aa0,C79810,CC3399");
	so.addParam("allowScriptAccess", "sameDomain");
	so.write("pie_chart_" + targetFrameObject);
}

function initBar(targetFrameObject, xSize, ySize, title, x_label, y_label, label, value, unit)
{
	var max=0;
	var values = value.split(',');
	for(var i=0;i<values.length;i++)
		if(parseInt(values[i])>max) max = parseInt(values[i]);
		
	document.getElementById(targetFrameObject).innerHTML="<div id='bar_chart_"+targetFrameObject+"' style='padding: 0px; margin:10px; border: 1px solid lightblue; width: " + xSize + "px; height: " + ySize + "px;' ></div>";
	var so = new SWFObject("/uoj/scripts/flash/chart/actionscript/open-flash-chart.swf", "ofc", xSize, ySize, "9", "#FFFFFF");
	title = title + ",{font-size: 16;color:#5284C7}";
	unit = "50,0x9933CC," + unit + ",10";
	so.addVariable("variables","true");
	so.addVariable("title",title);
	so.addVariable("y_label_size","15");
	so.addVariable("y_ticks","5,10,4");
	so.addVariable("bar",unit);
	so.addVariable("values",value);
	so.addVariable("x_labels",label);
	so.addVariable("x_axis_steps","1");
    so.addVariable("x_legend",x_label+",12,#736AFF");
    so.addVariable("y_legend",y_label+",12,#736AFF");
	so.addVariable("y_max",max);
	so.addParam("allowScriptAccess", "always" );//"sameDomain");
	
	so.write("bar_chart_" + targetFrameObject);
}

function initAreaHollow(targetFrameObject, xSize, ySize,title, x_label, y_label, label, value, unit)
{
	var max=0;
	var values = value.split(',');
	for(var i=0;i<values.length;i++)
		if(parseInt(values[i])>max) max = parseInt(values[i]);

	document.getElementById(targetFrameObject).innerHTML="<div id='area_hollow_chart_"+targetFrameObject+"' style='padding: 0px; margin:10px; border: 1px solid lightblue; width: " + xSize + "px; height: " + ySize + "px;' ></div>";
	var so = new SWFObject("/uoj/scripts/flash/chart/actionscript/open-flash-chart.swf", "ofc", xSize, ySize, "9", "#FFFFFF");
	
	title = title + ",{font-size: 16;color:#5284C7}";
	unit = "2,3,25,#CC3399," + unit + ",10";
	so.addVariable("variables","true");
	so.addVariable("title",title);
	so.addVariable("y_label_size","15");
	so.addVariable("y_ticks","5,10,4");
	so.addVariable("area_hollow",unit);
	so.addVariable("values",value);
	so.addVariable("x_labels",label);
	so.addVariable("x_axis_steps","1");
    so.addVariable("x_legend",x_label+",12,#736AFF");
    so.addVariable("y_legend",y_label+",12,#736AFF");
	so.addVariable("y_max",max);
	
	so.addParam("allowScriptAccess", "always" );//"sameDomain");
	so.write("area_hollow_chart_" + targetFrameObject);
}

function initLine(targetFrameObject, xSize, ySize, title, x_label, y_label, label, value, unit)
{
	var max=0;
	var values = value.split(',');
	for(var i=0;i<values.length;i++)
		if(parseInt(values[i])>max) max = parseInt(values[i]);

	document.getElementById(targetFrameObject).innerHTML="<div id='line_chart_"+targetFrameObject+"' style='padding: 0px; margin:10px; border: 1px solid lightblue; width: " + xSize + "px; height: " + ySize + "px;' ></div>";
	var so = new SWFObject("/uoj/scripts/flash/chart/actionscript/open-flash-chart.swf", "ofc", xSize, ySize, "9", "#FFFFFF");
	unit = "2,#CC3399," + unit + ",10,5";
	
	title = title + ",{font-size: 16;color:#5284C7}";
	so.addVariable("variables","true");
	
	so.addVariable("title",title);
    so.addVariable("x_axis_colour","#D9E4F3");
	so.addVariable("x_axis_steps","1");
    so.addVariable("x_grid_colour","#E8EDF8");
	so.addVariable("x_labels",label);
    so.addVariable("x_label_style","12,#5B719D");
    so.addVariable("x_legend",x_label+",12,#736AFF");
	so.addVariable("y_label_size","15");
    so.addVariable("y_axis_colour","#D9E4F3");
    so.addVariable("y_grid_colour","#E8EDF8");
	so.addVariable("y_max",max);
    so.addVariable("y_label_style","12,#5B719D");
    so.addVariable("y_legend",y_label+",12,#736AFF");
  	so.addVariable("y_ticks","5,10,4");
	so.addVariable("values",value);
    so.addVariable("line_dot",unit);
    
	so.write("line_chart_" + targetFrameObject);
}