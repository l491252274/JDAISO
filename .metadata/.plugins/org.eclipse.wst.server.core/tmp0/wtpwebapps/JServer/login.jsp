<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/scripts/login.js"%>
<%@page import="com.unlimited.oj.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.unlimited.oj.util.ApplicationConfig"%>
<%@ page pageEncoding="UTF-8"%>
<%
    Boolean jump = false;
    String jumptourl = "";

	if(request.getParameter("admin")==null && ApplicationConfig.getValue("JumpToURL")!=null)
	{
		jump = true;
		jumptourl = (String)ApplicationConfig.getValue("JumpToURL");
	}

	pageContext.setAttribute("jump", jump);
	pageContext.setAttribute("jumptourl", jumptourl);
%>

<c:if test="${jump}">
<html>
	<META http-equiv="refresh" content="0;URL=${jumptourl}"> 
</html>
</c:if>

<head>
    <title><fmt:message key="login.title"/></title>
    <meta name="heading" content="<fmt:message key='login.heading'/>"/>
    <meta name="menu" content="MainMenu"/>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/layout-1col.css'/>" />
	<link href="style/authority/login_css.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#login_sub").click(function(){
			$("#submitForm").attr("action", "index.html").submit();
		});
	});
	
	function EnterPress(e){
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			$("#submitForm").attr("action", "index.html").submit();
		} 
	} 
</script>

<SCRIPT type="text/javascript" src="scripts/vkboard/vkboard.js"></SCRIPT>
<SCRIPT><!--

   // This example shows how to handle multiple INPUT
   // fields with a single vkeyboard.

   // Parts of the following code are taken from the DocumentSelection
   // library (http://debugger.ru/projects/browserextensions/documentselection)
   // by Ilya Lebedev. DocumentSelection is distributed under LGPL license
   // (http://www.gnu.org/licenses/lgpl.html).

   // 'source' is the field which is currently focused:
   var source = null, vkb = null, opened = false, insertionS = 0, insertionE = 0;

   var userstr = navigator.userAgent.toLowerCase();
   var safari = (userstr.indexOf('applewebkit') != -1);
   var gecko  = (userstr.indexOf('gecko') != -1) && !safari;
   var standr = gecko || window.opera || safari;

   // This function retrieves the source element
   // for the given event object:
   function get_event_source(e)
   {
     var event = e || window.event;
     return event.srcElement || event.target;
   }

   // This function binds 'handler' function to the
   // 'eventType' event of the 'elem' element:
   function setup_event(elem, eventType, handler)
   {
     return (elem.attachEvent) ? elem.attachEvent("on" + eventType, handler) : ((elem.addEventListener) ? elem.addEventListener(eventType, handler, false) : false);
   }

   // By focusing the INPUT field we set the 'source'
   // to the newly focused field:
   function focus_keyboard(e)
   {
     source = get_event_source(e);
   }

   // By "registering" the field we bind 'focus_keyboard'
   // function to 'focus' event of the given INPUT field:
   function register_field(id)
   {
     setup_event(document.getElementById(id), "focus", focus_keyboard);
   }

   // The same creation precedure as usual, with a single
   // difference that we're "registering" 3 INPUT fields:
   function init()
   {
       // Note: all parameters, starting with 3rd, in the following
       // expression are equal to the default parameters for the
       // VKeyboard object. The only exception is 18th parameter
       // (flash switch), which is false by default.

       vkb = new VKeyboard("keyboard",    // container's id
					       keyb_callback, // reference to the callback function
                           true,          // create the arrow keys or not? (this and the following params are optional)
                           true,          // create up and down arrow keys?
                           false,         // reserved
                           true,          // create the numpad or not?
                           "",            // font name ("" == system default)
					       "14px",        // font size in px
                           "#000",        // font color
                           "#F00",        // font color for the dead keys
                           "#FFF",        // keyboard base background color
                           "#FFF",        // keys' background color
                           "#DDD",        // background color of switched/selected item
                           "#777",        // border color
                           "#CCC",        // border/font color of "inactive" key (key with no value/disabled)
                           "#FFF",        // background color of "inactive" key (key with no value/disabled)
                           "#F77",        // border color of the language selector's cell
                           true,          // show key flash on click? (false by default)
                           "#CC3300",     // font color during flash
                           "#FF9966",     // key background color during flash
                           "#CC3300",     // key border color during flash
                           false,         // embed VKeyboard into the page?
                           true,          // use 1-pixel gap between the keys?
                           0);            // index(0-based) of the initial layout

     // 'j_username' is "focused" by default:
     source = document.getElementById("j_username");

     register_field("j_username"); register_field("j_password"); register_field("j_code");

     source.focus();
   }

   function keyb_change()
   {
     //document.getElementById("switch").innerHTML = (opened ? "Show keyboard" : "Hide keyboard");
     opened = !opened;
     if(opened && vkb==null)
    	 init();

     vkb.Show(opened);
   }

   // Advanced callback function:
   //
   function keyb_callback(ch)
   {
     var val = source.value;

     switch(ch)
     {
       case "BackSpace":
         if(val.length)
         {
           var span = null;

           if(document.selection)
             span = document.selection.createRange().duplicate();

           if(span && span.text.length > 0)
           {
             span.text = "";
             getCaretPositions(source);
           }
           else
             deleteAtCaret(source);
         }

         break;

       case "<":
         if(insertionS > 0)
           setRange(source, insertionS - 1, insertionE - 1);

         break;

       case ">":
         if(insertionE < val.length)
           setRange(source, insertionS + 1, insertionE + 1);

         break;

       case "/\\":
         if(!standr) break;

         var prev  = val.lastIndexOf("\n", insertionS) + 1;
         var pprev = val.lastIndexOf("\n", prev - 2);
         var next  = val.indexOf("\n", insertionS);

         if(next == -1) next = val.length;
         var nnext = next - insertionS;

         if(prev > next)
         {
           prev  = val.lastIndexOf("\n", insertionS - 1) + 1;
           pprev = val.lastIndexOf("\n", prev - 2);
         }

         // number of chars in current line to the left of the caret:
         var left = insertionS - prev;

         // length of the prev. line:
         var plen = prev - pprev - 1;

         // number of chars in the prev. line to the right of the caret:
         var right = (plen <= left) ? 1 : (plen - left);

         var change = left + right;
         setRange(source, insertionS - change, insertionE - change);

         break;

       case "\\/":
         if(!standr) break;

         var prev  = val.lastIndexOf("\n", insertionS) + 1;
         var next  = val.indexOf("\n", insertionS);
         var pnext = val.indexOf("\n", next + 1);

         if( next == -1)  next = val.length;
         if(pnext == -1) pnext = val.length;

         if(pnext < next) pnext = next;

         if(prev > next)
            prev  = val.lastIndexOf("\n", insertionS - 1) + 1;

         // number of chars in current line to the left of the caret:
         var left = insertionS - prev;

         // length of the next line:
         var nlen = pnext - next;

         // number of chars in the next line to the left of the caret:
         var right = (nlen <= left) ? 0 : (nlen - left - 1);

         var change = (next - insertionS) + nlen - right;
         setRange(source, insertionS + change, insertionE + change);

         break;

       default:
         insertAtCaret(source, (ch == "Enter" ? (window.opera ? '\r\n' : '\n') : ch));
     }
   }

   // This function retrieves the position (in chars, relative to
   // the start of the text) of the edit cursor (caret), or, if
   // text is selected in the TEXTAREA, the start and end positions
   // of the selection.
   //
   function getCaretPositions(ctrl)
   {
     var CaretPosS = -1, CaretPosE = 0;

     // Mozilla way:
     if(ctrl.selectionStart || (ctrl.selectionStart == '0'))
     {
       CaretPosS = ctrl.selectionStart;
       CaretPosE = ctrl.selectionEnd;

       insertionS = CaretPosS == -1 ? CaretPosE : CaretPosS;
       insertionE = CaretPosE;
     }
     // IE way:
     else if(document.selection && ctrl.createTextRange)
     {
       var start = end = 0;
       try
       {
         start = Math.abs(document.selection.createRange().moveStart("character", -10000000)); // start

         if (start > 0)
         {
           try
           {
             var endReal = Math.abs(ctrl.createTextRange().moveEnd("character", -10000000));

             var r = document.body.createTextRange();
             r.moveToElementText(ctrl);
             var sTest = Math.abs(r.moveStart("character", -10000000));
             var eTest = Math.abs(r.moveEnd("character", -10000000));

             if ((ctrl.tagName.toLowerCase() != 'input') && (eTest - endReal == sTest))
               start -= sTest;
           }
           catch(err) {}
         }
       }
       catch (e) {}

       try
       {
         end = Math.abs(document.selection.createRange().moveEnd("character", -10000000)); // end
         if(end > 0)
         {
           try
           {
             var endReal = Math.abs(ctrl.createTextRange().moveEnd("character", -10000000));

             var r = document.body.createTextRange();
             r.moveToElementText(ctrl);
             var sTest = Math.abs(r.moveStart("character", -10000000));
             var eTest = Math.abs(r.moveEnd("character", -10000000));

             if ((ctrl.tagName.toLowerCase() != 'input') && (eTest - endReal == sTest))
              end -= sTest;
           }
           catch(err) {}
         }
       }
       catch (e) {}

       insertionS = start;
       insertionE = end
     }
   }

   function setRange(ctrl, start, end)
   {
     if(ctrl.setSelectionRange) // Standard way (Mozilla, Opera, Safari ...)
     {
       ctrl.setSelectionRange(start, end);
     }
     else // MS IE
     {
       var range;

       try
       {
         range = ctrl.createTextRange();
       }
       catch(e)
       {
         try
         {
           range = document.body.createTextRange();
           range.moveToElementText(ctrl);
         }
         catch(e)
         {
           range = null;
         }
       }

       if(!range) return;

       range.collapse(true);
       range.moveStart("character", start);
       range.moveEnd("character", end - start);
       range.select();
     }

     insertionS = start;
     insertionE = end;
   }

   function deleteSelection(ctrl)
   {
     if(insertionS == insertionE) return;

     var tmp = (document.selection && !window.opera) ? ctrl.value.replace(/\r/g,"") : ctrl.value;
     ctrl.value = tmp.substring(0, insertionS) + tmp.substring(insertionE, tmp.length);

     setRange(ctrl, insertionS, insertionS);
   }

   function deleteAtCaret(ctrl)
   {
     // if(insertionE < insertionS) insertionE = insertionS;
     if(insertionS != insertionE)
     {
       deleteSelection(ctrl);
       return;
     }

     if(insertionS == insertionE)
       insertionS = insertionS - 1;

     var tmp = (document.selection && !window.opera) ? ctrl.value.replace(/\r/g,"") : ctrl.value;
     ctrl.value = tmp.substring(0, insertionS) + tmp.substring(insertionE, tmp.length);

     setRange(ctrl, insertionS, insertionS);
   }

   // This function inserts text at the caret position:
   //
   function insertAtCaret(ctrl, val)
   {
     if(insertionS != insertionE) deleteSelection(ctrl);

     if(gecko && document.createEvent && !window.opera)
     {
       var e = document.createEvent("KeyboardEvent");

       if(e.initKeyEvent && ctrl.dispatchEvent)
       {
         e.initKeyEvent("keypress",        // in DOMString typeArg,
                        false,             // in boolean canBubbleArg,
                        true,              // in boolean cancelableArg,
                        null,              // in nsIDOMAbstractView viewArg, specifies UIEvent.view. This value may be null;
                        false,             // in boolean ctrlKeyArg,
                        false,             // in boolean altKeyArg,
                        false,             // in boolean shiftKeyArg,
                        false,             // in boolean metaKeyArg,
                        null,              // key code;
                        val.charCodeAt(0));// char code.

         ctrl.dispatchEvent(e);
       }
     }
     else
     {
       var tmp = (document.selection && !window.opera) ? ctrl.value.replace(/\r/g,"") : ctrl.value;
       ctrl.value = tmp.substring(0, insertionS) + val + tmp.substring(insertionS, tmp.length);
     }

     setRange(ctrl, insertionS + val.length, insertionS + val.length);
   }

 //--></SCRIPT>
</head>

<%
        pageContext.setAttribute("backgroud", ApplicationConfig.getValue("LoginBackgroud"));
        if(session.getAttribute("tryCount")==null)
			pageContext.setAttribute("showValidateCode", false);
        else
        {
        	Long tryCount= (Long)session.getAttribute("tryCount");
        	if(tryCount<3)
    			pageContext.setAttribute("showValidateCode", false);
        	else
    			pageContext.setAttribute("showValidateCode", true);
        }
%>

<script language="JavaScript" type="text/javascript">
if ((navigator.userAgent.indexOf('MSIE') >= 0) 
    && (navigator.userAgent.indexOf('Opera') < 0)){
    alert('IE can not work in the system, you can use Google browser or 360 browser (extra speed mode)!');
}
else if(navigator.userAgent.indexOf("Edge") > -1){
    alert('Edge can not work in the system, you can use Google browser or 360 browser (extra speed mode)!');
}
</script>


<DIV id="keyboard"></DIV>
<div id="login_center">

	<div id="login_area">
		<div id="login_box_" style="margin: 0 auto;width: 812px;height: 408px;background: url('images/login/${backgroud}') 0px 0px no-repeat;position: relative;">
<c:if test="${param.error != null}">
        <img src="${ctx}/images/iconWarning.gif" alt="<fmt:message key='icon.warning'/>" class="icon"/>
        <fmt:message key="errors.password.mismatch"/>
        <%--${sessionScope.SPRING_SECURITY_LAST_EXCEPTION_KEY.message}--%>
</c:if>		

				 
				
			<div id="login_form">
				<form method="post" id="loginForm" action="<c:url value='/j_security_check'/>"
				    onsubmit="saveUsername(this);return validateForm(this)">
  						<div id="login_tip">
						<span id="login_err" class="sty_txt2"></span>
					</div>
					<div>
				      <label for="j_username" class="required desc">
				           <fmt:message key="label.username"/> <span class="req">*</span>
				       </label><br/>
				       <input type="text" class="text medium" name="j_username" id="j_username" tabindex="1" />
					</div>
					<div>
				       <label for="j_password" class="required desc">
				           <fmt:message key="label.password"/> <span class="req">*</span>&nbsp;&nbsp;&nbsp;&nbsp;
				       </label><br/>
				       <input type="password" class="text medium" name="j_password" id="j_password" tabindex="2" />
					</div>
<c:if test="${showValidateCode}">
					<div id="divsource" style="display:block;">
				       <label for="j_code" class="required desc">
				           <fmt:message key="label.verificationCode"/> <span class="req">*</span>
				       </label><br/>
				       <input type="text" class="text medium" name="j_code" id="j_code" tabindex="2" /><br/>
				       <img src="ValidateCodeServlet" width="80" height="30" style="padding:5px 0px;"/>
					</div>
</c:if>				

				 
					<div>
				       <input type="submit" class="button" name="login" value="<fmt:message key='button.login'/>" tabindex="4" />&nbsp;&nbsp;&nbsp;
						<fmt:message key="login.passwordHint"/>
				       <c:if test="${appConfig['AccountCanRegister']=='TRUE' || appConfig['AccountCanRegister']=='True' || appConfig['AccountCanRegister']=='true'}">
				           <fmt:message key="login.signup">
				               <fmt:param><c:url value="/signup.html"/></fmt:param>
				           </fmt:message>
				       </c:if>
					</div>
				</form>
				 
				<center>
				<form method="post" id="guestForm" action="<c:url value='/j_security_check'/>"
					<s:hidden key="j_username" value="guest"/>
					<s:hidden key="j_password" value=""/>
					<input type="submit" class="button" name="login" value="<fmt:message key='guest.login'/>" tabindex="4" style="width:150px;border-width:0px;" board/>
				</form>
				</center>
				
				<P><A href="javascript:keyb_change()" onclick="javascript:blur()" id="switch" style="text-decoration:none;border-bottom: 1px dashed #0000F0;color:#0000F0"><fmt:message key="label.keyboard"/></A></P>
				<center>
				<br/><font color=#900000><fmt:message key='login.advise'/></font>		
				</center>
			</div>
		</div>
	</div>
</div>








