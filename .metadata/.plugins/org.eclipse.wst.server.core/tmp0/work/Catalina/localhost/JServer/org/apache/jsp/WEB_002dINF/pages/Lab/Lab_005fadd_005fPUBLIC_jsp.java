/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.26
 * Generated at: 2018-11-24 15:34:59 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.pages.lab;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Lab_005fadd_005fPUBLIC_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(20);
    _jspx_dependants.put("/WEB-INF/lib/displaytag-1.2.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("/WEB-INF/lib/standard-1.1.2.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/standard-1.1.2.jar!/META-INF/c.tld", Long.valueOf(1098682290000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/sitemesh-2.2.1.jar!/META-INF/sitemesh-decorator.tld", Long.valueOf(1084607714000L));
    _jspx_dependants.put("/WEB-INF/lib/spring-security-taglibs-2.0.1.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/standard-1.1.2.jar!/META-INF/fn.tld", Long.valueOf(1098682290000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/oscache-2.3.jar!/META-INF/taglib.tld", Long.valueOf(1141644300000L));
    _jspx_dependants.put("/common/taglibs.jsp", Long.valueOf(1527668059084L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/standard-1.1.2.jar!/META-INF/fmt.tld", Long.valueOf(1098682290000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/spring-security-taglibs-2.0.1.jar!/META-INF/security.tld", Long.valueOf(1209703464000L));
    _jspx_dependants.put("/WEB-INF/lib/sitemesh-2.2.1.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/displaytag-1.2.jar!/META-INF/displaytag.tld", Long.valueOf(1230360346000L));
    _jspx_dependants.put("/WEB-INF/appfuse.tld", Long.valueOf(1510546770000L));
    _jspx_dependants.put("/WEB-INF/lib/oscache-2.3.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/sitemesh-2.2.1.jar!/META-INF/sitemesh-page.tld", Long.valueOf(1084607714000L));
    _jspx_dependants.put("/WEB-INF/lib/struts2-core-2.0.11.1.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/struts-menu-2.4.3.jar!/META-INF/struts-menu-el.tld", Long.valueOf(1180670192000L));
    _jspx_dependants.put("/WEB-INF/lib/struts-menu-2.4.3.jar", Long.valueOf(1510546770000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/struts2-core-2.0.11.1.jar!/META-INF/struts-tags.tld", Long.valueOf(1204458594000L));
    _jspx_dependants.put("jar:file:/C:/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/JServer/WEB-INF/lib/standard-1.1.2.jar!/META-INF/sql.tld", Long.valueOf(1098682290000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
    _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
    _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write('\r');
      out.write('\n');
      if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script>\r\n");
      out.write("function init(){\r\n");
      out.write("\tsetSize();\r\n");
      out.write("}\r\n");
      out.write("function setSize() { // check all the checkboxes in the list\r\n");
      out.write("  for (var i=0;i<document.forms[0].elements.length;i++) {\r\n");
      out.write("    var e = document.forms[0].elements[i];\r\n");
      out.write("        var eName = e.name;\r\n");
      out.write("        if ((e.type.indexOf(\"textarea\") == 0)) {\r\n");
      out.write("        \te.style.width=\"95%\";\r\n");
      out.write("        }\r\n");
      out.write("    } \r\n");
      out.write("}\r\n");
      out.write("window.onresize = setSize;\r\n");
      out.write("</script>\r\n");
      out.write("<head>\r\n");
      out.write("    <meta name=\"menu\" content=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.menu}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"/>\r\n");
      out.write("    <meta name=\"submenu\" content=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${param.submenu}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\"/>\r\n");
      out.write("</head>\r\n");
      out.write("<body onload=\"init();\">\r\n");
      out.write("\t<h1><p align=\"center\">Batch Lab Users</p></h1>\r\n");
      out.write("\t<p align=\"center\">\r\n");
      out.write("\t<table width=\"100%\" border=\"0\" align=\"center\">\r\n");
      out.write("\t\t<tr><td>\r\n");
      out.write("\t\t<div id=\"divsource\" style=\"display:block;\">\r\n");
      out.write("\t\t\t");
      if (_jspx_meth_s_005fform_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t</td></tr>\r\n");
      out.write("\t</table>\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fset_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /common/taglibs.jsp(15,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("ctx");
    // /common/taglibs.jsp(15,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.Object.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f1.setParent(null);
    // /common/taglibs.jsp(16,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setVar("datePattern");
    int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
    if (_jspx_eval_c_005fset_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_c_005fset_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_c_005fset_005f1.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_c_005fset_005f1.doInitBody();
      }
      do {
        if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fset_005f1, _jspx_page_context))
          return true;
        int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_c_005fset_005f1 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_c_005fset_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
    return false;
  }

  private boolean _jspx_meth_fmt_005fmessage_005f0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_005fset_005f1, javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  fmt:message
    org.apache.taglibs.standard.tag.rt.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.rt.fmt.MessageTag) _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.rt.fmt.MessageTag.class);
    _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
    _jspx_th_fmt_005fmessage_005f0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_005fset_005f1);
    // /common/taglibs.jsp(16,25) name = key type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_fmt_005fmessage_005f0.setKey("date.format");
    int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
    if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
    return false;
  }

  private boolean _jspx_meth_s_005fform_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  s:form
    org.apache.struts2.views.jsp.ui.FormTag _jspx_th_s_005fform_005f0 = (org.apache.struts2.views.jsp.ui.FormTag) _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction.get(org.apache.struts2.views.jsp.ui.FormTag.class);
    _jspx_th_s_005fform_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005fform_005f0.setParent(null);
    // /WEB-INF/pages/lab/Lab_add_PUBLIC.jsp(28,3) name = id type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fform_005f0.setId("form1");
    // /WEB-INF/pages/lab/Lab_add_PUBLIC.jsp(28,3) name = name type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fform_005f0.setName("form1");
    // /WEB-INF/pages/lab/Lab_add_PUBLIC.jsp(28,3) name = action type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fform_005f0.setAction("lab_Lab_add_PUBLIC");
    // /WEB-INF/pages/lab/Lab_add_PUBLIC.jsp(28,3) name = method type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fform_005f0.setMethod("post");
    int _jspx_eval_s_005fform_005f0 = _jspx_th_s_005fform_005f0.doStartTag();
    if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.pushBody();
        _jspx_th_s_005fform_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent) out);
        _jspx_th_s_005fform_005f0.doInitBody();
      }
      do {
        out.write("\r\n");
        out.write("\t\t\t\t<h1><p align=\"center\">Format:&nbsp;Lab id,&nbsp;Room id,&nbsp;floor</p></h1>\r\n");
        out.write("\t\t\t\tLAB ID:<input type = \"text\" name=\"labid\"><br>\r\n");
        out.write("\t\t\t\tROOM ID:<input type = \"text\" name=\"roomid\"><br>\r\n");
        out.write("\t\t\t\tFlOOR:<input type = \"text\" name=\"floor\"><br>\r\n");
        out.write("\t\t\t\t<p align=center><font color=red>per Lab per line</font></p>\r\n");
        out.write("\t\t\t\t<p align=center><input type=submit name=submit value=submit><INPUT type=reset value=Reset name=reset></p>\r\n");
        out.write("\t\t\t");
        int evalDoAfterBody = _jspx_th_s_005fform_005f0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
      if (_jspx_eval_s_005fform_005f0 != javax.servlet.jsp.tagext.Tag.EVAL_BODY_INCLUDE) {
        out = _jspx_page_context.popBody();
      }
    }
    if (_jspx_th_s_005fform_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction.reuse(_jspx_th_s_005fform_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fform_0026_005fname_005fmethod_005fid_005faction.reuse(_jspx_th_s_005fform_005f0);
    return false;
  }
}
