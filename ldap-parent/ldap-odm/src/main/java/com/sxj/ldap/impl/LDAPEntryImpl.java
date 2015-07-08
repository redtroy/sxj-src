package com.sxj.ldap.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapName;

import org.slf4j.LoggerFactory;

import com.sxj.ldap.ILdapEntry;
import com.sxj.ldap.LdapManager;
import com.sxj.ldap.annotations.Manager;
import com.sxj.ldap.exception.LdapNamingException;
import com.sxj.ldap.exception.ObjectClassNotSupportedException;

public class LDAPEntryImpl implements ILdapEntry, Serializable
{
    static final org.slf4j.Logger logger = LoggerFactory.getLogger(LDAPEntryImpl.class);
    
    protected boolean modified;
    
    protected boolean isNew;
    
    protected LdapName dn;
    
    protected List objectClasses;
    
    protected LinkedHashMap modificationItems;
    
    @Manager
    protected LdapManager manager;
    
    /**
     * This contains all of the attributes for the object
     */
    protected Attributes attributes;
    
    /**
     * Creates an LDAPObject that has all of the attributes and their values in
     * the order in which they were found in LDAP.  If you want a sorted
     * version, just convert them to another utility class, such as TreeSet, or
     * TreeMap.
     *
     * @param attributes the LDAP attributes object as returned by the JNDI
     *                   APIs.
     * @param dn distinguished name of the object.
     *
     * @throws NamingException when any errors occur
     */
    public LDAPEntryImpl(final Attributes attributes, final LdapName dn)
            throws NamingException
    {
        this.attributes = attributes;
        logger.debug("dn: " + dn);
        this.dn = dn;
        verifyObject();
        
        modificationItems = new LinkedHashMap();
    }
    
    /**
     * Loads the given attribute values into this object.
     *
     * @param attributes the attributes to load
     *
     * @throws NamingException if a JNDI error occurs.
     */
    private void loadAttributes(final Attributes attributes)
            throws NamingException
    {
        final NamingEnumeration attrs = attributes.getAll();
        while (attrs.hasMore())
        {
            final Attribute attr;
            attr = (Attribute) attrs.next();
            final List destValues; // dest for all values of current attribute
            final NamingEnumeration srcValues; // all values of current attribute
            destValues = new Vector(attr.size());
            srcValues = attr.getAll();
            
            while (srcValues.hasMore())
            {
                destValues.add(srcValues.next());
            }
            this.attributes.put(attr.getID(), destValues);
            logger.debug(attr.getID() + ": " + destValues.toString());
        }
    }
    
    public boolean equals(final Object obj)
    {
        return super.equals(obj);
    }
    
    /**
     * Calling this constructor implies that the object is brand new, and
     * the save() method should act accordingly.
     */
    /*    public LDAPObjectImpl(Map attributes) throws NamingException
        {
            this.attributes = new LinkedHashMap(attributes);
            initialize();
        }*/
    
    public LDAPEntryImpl()
    {
        // do nothing, probably called by serialization?
    }
    
    private void verifyObject() throws NamingException
    {
        objectClasses = getAttributeValues("objectClass");
        if (objectClasses == null)
        {
            throw new ObjectClassNotSupportedException("perhaps you forgot "
                    + "to request the 'objectClass' attribute as a returning "
                    + "attribute?  This is a requirement");
        }
        if (objectClasses.size() == 0)
        {
            logger.error(getStringValue("cn") + " has no objectClass?");
        }
        else
        {
            logger.debug(getStringValue("cn") + " has objectClasses: "
                    + objectClasses.toString());
        }
    }
    
    /**
     * Converts this object to the given instance.
     *
     * @param type the object type to convert to
     *
     * @return the new object.
     *
     * @throws NamingException if conversion fails due to the proper objectClass
     *                         not being setup.
     */
    public ILdapEntry convertInstance(final int type) throws NamingException
    {
        //        return LdapManager.createInstance(attributes, type, getDn());
        return null;
    }
    
    public List getObjectClasses()
    {
        return objectClasses;
    }
    
    public String getCN()
    {
        return getStringValue("cn");
    }
    
    public LdapName getDn()
    {
        return dn;
    }
    
    public String getDescription()
    {
        return getStringValue("description");
    }
    
    public String getStringAttribute(final Attributes attributes,
            final String attribute) throws NamingException
    {
        final Attribute temp;
        final String attributeValue;
        temp = attributes.get(attribute);
        if (temp != null)
        {
            attributeValue = (String) temp.get();
            logger.debug(attribute + ": " + getStringValue("cn"));
        }
        else
        {
            attributeValue = null;
        }
        
        return attributeValue;
    }
    
    public List getAttributeValues(final String attribute)
    {
        final Attribute ldapAttribute;
        List values = null;
        
        ldapAttribute = this.attributes.get(attribute);
        try
        {
            if (ldapAttribute != null && ldapAttribute.size() != 0)
            {
                values = Collections.list(ldapAttribute.getAll());
            }
        }
        catch (NamingException e)
        {
            throw new LdapNamingException(e);
        }
        
        if (values == null)
            values = new Vector();
        return values;
    }
    
    public String getStringValue(final String attribute)
    {
        final Attribute ldapAttribute;
        try
        {
            ldapAttribute = this.attributes.get(attribute);
            if (ldapAttribute != null && ldapAttribute.size() != 0)
                return (String) ldapAttribute.get(0);
            else
            {
                return null;
            }
        }
        catch (NamingException e)
        {
            throw new LdapNamingException(e);
        }
        
    }
    
    public void modifyAttribute(final int operation, final String attribute,
            final Object value)
    {
        modifyBatchAttribute(operation, attribute, value);
        modifyBatchAttributes(); // run the attribute operation
    }
    
    public void modifyBatchAttribute(final int operation,
            final String attribute, final Object value)
    {
        final Attribute newAttribute;
        ModificationItem modItem;
        final int mod_op;
        
        switch (operation)
        {
            case ADD_ATTRIBUTE:
                mod_op = DirContext.ADD_ATTRIBUTE;
                break;
            case REPLACE_ATTRIBUTE:
                mod_op = DirContext.REPLACE_ATTRIBUTE;
                break;
            case REMOVE_ATTRIBUTE:
                mod_op = DirContext.REMOVE_ATTRIBUTE;
                break;
            default:
                mod_op = DirContext.ADD_ATTRIBUTE;
        }
        
        modItem = (ModificationItem) modificationItems.get(attribute);
        if (modItem == null)
        { // first time we are doing something with this attribute
            newAttribute = new BasicAttribute(attribute, value);
            modItem = new ModificationItem(mod_op, newAttribute);
        }
        else
        { // we will add it to the attribute values
            if (modItem.getModificationOp() != mod_op)
            { // make sure they aren't changing their mind on which op
                throw new IllegalArgumentException(
                        "error, operation does not match previous batch items");
            }
            
            modItem.getAttribute().add(value);
        }
        modified = true;
        modificationItems.put(attribute, modItem);
    }
    
    public void modifyBatchAttributes()
    {
        modifyBatchAttributes(manager.getBindDN(), manager.getBindPassword());
    }
    
    public void modifyBatchAttributes(final String bindDN,
            final String bindPassword)
    { // BEGIN modifyBatchAttributes()
        DirContext ldapContext = null;
        
        if (modificationItems.size() == 0)
        {
            throw new IllegalStateException("No modification items for batch");
        }
        
        try
        {
            final Object[] tempModItems;
            final ModificationItem[] modItems;
            tempModItems = modificationItems.values().toArray();
            modItems = new ModificationItem[tempModItems.length];
            for (int index = 0; index < tempModItems.length; index++)
            { // convert to ModificationItem array
                modItems[index] = (ModificationItem) tempModItems[index];
            }
            
            ldapContext = manager.getConnection(bindDN, bindPassword);
            ldapContext.modifyAttributes(getDn(), modItems);
            
            /**
             * Update the attributes in memory
             */
            for (final ModificationItem modItem : modItems)
            {
                final Attribute attribute;
                attribute = modItem.getAttribute();
                updateAttribute(attribute.getID());
            }
        }
        catch (NamingException namingException)
        {
            throw new LdapNamingException(namingException);
        }
        catch (Exception exception)
        {
            throw new LdapNamingException("error modifying attributes",
                    exception);
        }
        finally
        {
            try
            {
                if (ldapContext != null)
                {
                    ldapContext.close();
                }
            }
            catch (NamingException namingException)
            {
                manager.logNamingException(namingException);
            }
            
            // recreate empty batch list
            modificationItems = new LinkedHashMap();
        }
    } // END modifyBatchAttributes()
    
    public void save()
    {
        if (modified)
        {
            modified = false;
            modifyBatchAttributes();
        }
    }
    
    /**
     * Updates the specified attribute from LDAP.
     * <p/>
     * TODO : Instead of using LDAPFactory.getAttributes, using
     * DirContext.getAttributes().  Then we can remove the getAttributes().
     *
     * @param attrName the name of the attribute
     *
     * @throws NamingException if any LDAP errors occur.
     */
    protected void updateAttribute(final String attrName)
            throws NamingException
    {
        final String[] returningAttributes;
        final Attributes returnedAttributes;
        
        returningAttributes = new String[1];
        returningAttributes[0] = attrName;
        returnedAttributes = manager.getAttributes(dn, returningAttributes);
        
        if (returnedAttributes.size() == 1)
        { // only attempt to load the attributes if the search found them.
          // the attribute to update
            this.attributes.put(returnedAttributes.get(attrName));
        }
    }
    
    /**
     * Updates the attributes from LDAP.
     * <p/>
     * TODO : in order for this to work properly, we MUST have all possible
     * attribute names stored in this object as statics so that we will know
     * what to lookup in LDAP.  Either that, or we need to just load all
     * attributes.
     *
     * @throws NamingException - should be replaced with LdapNamingException?
     */
    protected void updateObject() throws NamingException
    { // BEGIN updateAttribute()
    
        throw new RuntimeException("not implemented");
        /*        String[] dnSplit;
                String[] attr_value;
                String baseDN;
                String[] returningAttributes;
                Map entries;

                dnSplit = dn.split(",", 2);
                attr_value = dnSplit[0].split("=");
                baseDN = dnSplit[1];

                Object[] attributes;
                if (this.attributes.size() == 0)
                {
                    throw new RuntimeException("no attributes to update");
                }

                attributes = this.attributes.keySet().toArray();
                returningAttributes = new String[attributes.length + 1];
                for (int index = 0; index < attributes.length; index++)
                {   // convert attribute names to a string array
                    returningAttributes[index] = (String) attributes[index];
                }

                returningAttributes[returningAttributes.length] = "objectClass";

                // search for the object in LDAP
                entries = LDAPFactory.search(baseDN, "(" + attr_value[0] + "=" +
                    attr_value[1] + ")", attr_value[0], returningAttributes,
                    LDAPFactory.LDAP_OBJECT, LDAPFactory.SORTED_ORDER);
                LDAPObjectImpl object ;
                object = (LDAPObjectImpl) entries.get(attr_value[1]);
                if (object == null)
                {
                    *//**
                      * this REALLY should not occur.  It probably means someone
                      * removed the LDAP entry since this object was instantiated.
                      */
        /*
        throw new RuntimeException("error updating object data from LDAP");
        }*/
        //        this.attributes = object.attributes;
    } // END updateAttribute()
    
    protected boolean isObjectClass(final String objectClass)
    {
        return objectClasses.contains(objectClass);
    }
    
    public Attributes getBindAttributes()
    {
        return attributes;
    }
    
    @Override
    public void setDn(LdapName dn)
    {
    }
    
    @Override
    public String getCn()
    {
        return null;
    }
    
    @Override
    public void setCn(String cn)
    {
    }
    
    public String toString()
    {
        return attributes.toString();
    }
}
