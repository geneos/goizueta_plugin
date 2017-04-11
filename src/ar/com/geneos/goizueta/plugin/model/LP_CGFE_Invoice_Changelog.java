/** Modelo Generado - NO CAMBIAR MANUALMENTE - Disytel */
package ar.com.geneos.goizueta.plugin.model;
import org.openXpertya.model.*;
import java.util.logging.Level;
 import java.util.*;
import java.sql.*;
import java.math.*;
import org.openXpertya.util.*;
/** Modelo Generado por CGFE_Invoice_Changelog
 *  @author Comunidad de Desarrollo Libertya*         *Basado en Codigo Original Modificado, Revisado y Optimizado de:*         * Jorg Janke 
 *  @version  - 2017-04-11 09:32:41.661 */
public class LP_CGFE_Invoice_Changelog extends org.openXpertya.model.PO
{
/** Constructor estándar */
public LP_CGFE_Invoice_Changelog (Properties ctx, int CGFE_Invoice_Changelog_ID, String trxName)
{
super (ctx, CGFE_Invoice_Changelog_ID, trxName);
/** if (CGFE_Invoice_Changelog_ID == 0)
{
setAD_Session_ID (0);
setCGFE_Invoice_Changelog_ID (0);
setC_Invoice_ID (0);
setDocumentNo (null);
setsuccess (false);
}
 */
}
/** Load Constructor */
public LP_CGFE_Invoice_Changelog (Properties ctx, ResultSet rs, String trxName)
{
super (ctx, rs, trxName);
}
/** AD_Table_ID */
public static final int Table_ID = M_Table.getTableID("CGFE_Invoice_Changelog");

/** TableName=CGFE_Invoice_Changelog */
public static final String Table_Name="CGFE_Invoice_Changelog";

protected static KeyNamePair Model = new KeyNamePair(Table_ID,"CGFE_Invoice_Changelog");
protected static BigDecimal AccessLevel = new BigDecimal(3);

/** Load Meta Data */
protected POInfo initPO (Properties ctx)
{
POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
return poi;
}
public String toString()
{
StringBuffer sb = new StringBuffer ("LP_CGFE_Invoice_Changelog[").append(getID()).append("]");
return sb.toString();
}
/** Set Session.
User Session Online or Web */
public void setAD_Session_ID (int AD_Session_ID)
{
set_Value ("AD_Session_ID", new Integer(AD_Session_ID));
}
/** Get Session.
User Session Online or Web */
public int getAD_Session_ID() 
{
Integer ii = (Integer)get_Value("AD_Session_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set CGFE_Invoice_Changelog_ID */
public void setCGFE_Invoice_Changelog_ID (int CGFE_Invoice_Changelog_ID)
{
set_ValueNoCheck ("CGFE_Invoice_Changelog_ID", new Integer(CGFE_Invoice_Changelog_ID));
}
/** Get CGFE_Invoice_Changelog_ID */
public int getCGFE_Invoice_Changelog_ID() 
{
Integer ii = (Integer)get_Value("CGFE_Invoice_Changelog_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Invoice.
Invoice Identifier */
public void setC_Invoice_ID (int C_Invoice_ID)
{
set_Value ("C_Invoice_ID", new Integer(C_Invoice_ID));
}
/** Get Invoice.
Invoice Identifier */
public int getC_Invoice_ID() 
{
Integer ii = (Integer)get_Value("C_Invoice_ID");
if (ii == null) return 0;
return ii.intValue();
}
/** Set Document No.
Document sequence NUMERIC of the document */
public void setDocumentNo (String DocumentNo)
{
if (DocumentNo == null) throw new IllegalArgumentException ("DocumentNo is mandatory");
if (DocumentNo.length() > 30)
{
log.warning("Length > 30 - truncated");
DocumentNo = DocumentNo.substring(0,30);
}
set_Value ("DocumentNo", DocumentNo);
}
/** Get Document No.
Document sequence NUMERIC of the document */
public String getDocumentNo() 
{
return (String)get_Value("DocumentNo");
}
/** Set msgerror */
public void setmsgerror (String msgerror)
{
if (msgerror != null && msgerror.length() > 200)
{
log.warning("Length > 200 - truncated");
msgerror = msgerror.substring(0,200);
}
set_Value ("msgerror", msgerror);
}
/** Get msgerror */
public String getmsgerror() 
{
return (String)get_Value("msgerror");
}
/** Set requestxml */
public void setrequestxml (String requestxml)
{
if (requestxml != null && requestxml.length() > 4000)
{
log.warning("Length > 4000 - truncated");
requestxml = requestxml.substring(0,4000);
}
set_Value ("requestxml", requestxml);
}
/** Get requestxml */
public String getrequestxml() 
{
return (String)get_Value("requestxml");
}
/** Set responsexml */
public void setresponsexml (String responsexml)
{
if (responsexml != null && responsexml.length() > 4000)
{
log.warning("Length > 4000 - truncated");
responsexml = responsexml.substring(0,4000);
}
set_Value ("responsexml", responsexml);
}
/** Get responsexml */
public String getresponsexml() 
{
return (String)get_Value("responsexml");
}
/** Set success */
public void setsuccess (boolean success)
{
set_Value ("success", new Boolean(success));
}
/** Get success */
public boolean issuccess() 
{
Object oo = get_Value("success");
if (oo != null) 
{
 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
 return "Y".equals(oo);
}
return false;
}
}
