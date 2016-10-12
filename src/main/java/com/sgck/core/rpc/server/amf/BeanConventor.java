package com.sgck.core.rpc.server.amf;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sgck.core.exception.DSException;

import flex.messaging.io.amf.ASObject;

public class BeanConventor
{

	/**
	 * Special array value used by <code>mapColumnsToProperties</code> that
	 * indicates there is no bean property that matches a column from a
	 * <code>ResultSet</code>.
	 */
	protected static final int PROPERTY_NOT_FOUND = -1;

	/**
	 * Set a bean's primitive properties to these defaults when SQL NULL
	 * is returned.  These are the same as the defaults that ResultSet get*
	 * methods return in the event of a NULL column.
	 */
	private static final Map<Class<?>, Object> primitiveDefaults = new HashMap<Class<?>, Object>();

	static
	{
		primitiveDefaults.put(Integer.TYPE, Integer.valueOf(0));
		primitiveDefaults.put(Short.TYPE, Short.valueOf((short) 0));
		primitiveDefaults.put(Byte.TYPE, Byte.valueOf((byte) 0));
		primitiveDefaults.put(Float.TYPE, Float.valueOf(0f));
		primitiveDefaults.put(Double.TYPE, Double.valueOf(0d));
		primitiveDefaults.put(Long.TYPE, Long.valueOf(0L));
		primitiveDefaults.put(Boolean.TYPE, Boolean.FALSE);
		primitiveDefaults.put(Character.TYPE, Character.valueOf((char) 0));
	}

	/**
	 * Constructor for BeanConventor.
	 */
	public BeanConventor()
	{
		super();
	}

	/**
	 * Convert a <code>ResultSet</code> row into a JavaBean.  This
	 * implementation uses reflection and <code>BeanInfo</code> classes to
	 * match column names to bean property names.  Properties are matched to
	 * columns based on several factors:
	 * <br/>
	 * <ol>
	 * <li>
	 * The class has a writable property with the same name as a column.
	 * The name comparison is case insensitive.
	 * </li>
	 * <p/>
	 * <li>
	 * The column type can be converted to the property's set method
	 * parameter type with a ResultSet.get* method.  If the conversion fails
	 * (ie. the property was an int and the column was a Timestamp) an
	 * Exception is thrown.
	 * </li>
	 * </ol>
	 * <p/>
	 * <p>
	 * Primitive bean properties are set to their defaults when SQL NULL is
	 * returned from the <code>ResultSet</code>.  Numeric fields are set to 0
	 * and booleans are set to false.  Object bean properties are set to
	 * <code>null</code> when SQL NULL is returned.  This is the same behavior
	 * as the <code>ResultSet</code> get* methods.
	 * </p>
	 *
	 * @param <T>  The type of bean to create
	 * @param amfObj   ResultSet that supplies the bean data
	 * @param type Class from which to create the bean instance
	 * @return the newly created bean
	 * @throws Exception if a database access error occurs
	 */
	public <T> T toBean(ASObject amfObj, Class<T> type) throws Exception
	{

		PropertyDescriptor[] props = this.propertyDescriptors(type);

		return this.createBean(amfObj, type, props);
	}

	/**
	 * Convert AMF Array to Specified-class type Array.
	 *
	 * @param Class<T>         Specified-class type
	 * @param origArray        AMF Array Object.
	 * @return the result is null maybe.
	 */
	public <T> T[] arrayConvert(Class<T> wantedArrayItemClass,List origArray) throws Exception{
		int len = origArray.size();
		Object origObj = null;
		T[] ret = (T[])Array.newInstance(wantedArrayItemClass,len);	
		
		for(int i = 0; i < len; i++){
			origObj = origArray.get(i);
			if(origObj.getClass() == wantedArrayItemClass){
				ret[i] = (T)origObj;
			}
			else if (origObj instanceof ASObject){
				ret[i] = (T)toBean((ASObject) origObj, wantedArrayItemClass);
			}else{
				ret[i] = null;
			}
		}
		return ret;
	}
	
	/**
	 * Creates a new object and initializes its fields from the ResultSet.
	 *
	 * @param <T>              The type of bean to create
	 * @param amfObj               The result set.
	 * @param type             The bean type (the return type of the object).
	 * @param props            The property descriptors.
	 * @return An initialized object.
	 * @throws Exception if a database error occurs.
	 */
	private <T> T createBean(ASObject amfObj, Class<T> type, PropertyDescriptor[] props)
			throws Exception
	{

		T bean = this.newInstance(type);

        /*for (int i = 1; i < columnToProperty.length; i++) {

            if (columnToProperty[i] == PROPERTY_NOT_FOUND) {
                continue;
            }

            PropertyDescriptor prop = props[columnToProperty[i]];
            Class<?> propType = prop.getPropertyType();

            Object value = this.processColumn(rs, i, propType);

            if (propType != null && value == null && propType.isPrimitive()) {
                value = primitiveDefaults.get(propType);
            }

            this.callSetter(bean, prop, value);
        }*/

		String propertyName;
		Class<?> propType;
		for (int i = 0; i < props.length; i++)
		{
			PropertyDescriptor prop = props[i];
			propertyName = prop.getName();
			propType = prop.getPropertyType();
			
			if (propertyName.equals("class")){
				continue;
			}

			Object value = amfObj.get(propertyName);
			
			if (value == null)
			{
				/*bean = null;
				break;*/
				continue;
			}
			else
			{
				try{
					if(propType.isArray() && value instanceof java.util.List){
						value = arrayConvert(propType.getComponentType(),(List)value);
					}
					this.callSetter(bean, prop, value);
				}catch(Exception e){
				}
			}
		}

		return bean;
	}

	/**
	 * Calls the setter method on the target object for the given property.
	 * If no setter method exists for the property, this method does nothing.
	 *
	 * @param target The object to set the property on.
	 * @param prop   The property to set.
	 * @param value  The value to pass into the setter.
	 * @throws Exception if an error occurs setting the property.
	 */
	private void callSetter(Object target, PropertyDescriptor prop, Object value)
			throws Exception
	{

		Method setter = prop.getWriteMethod();

		if (setter == null)
		{
			return;
		}

		Class<?>[] params = setter.getParameterTypes();
		try
		{
			// convert types for some popular ones
			if (value != null)
			{
				if (value instanceof java.util.Date)
				{
					if (params[0].getName().equals("java.sql.Date"))
					{
						value = new java.sql.Date(((java.util.Date) value).getTime());
					}
					else if (params[0].getName().equals("java.sql.Time"))
					{
						value = new java.sql.Time(((java.util.Date) value).getTime());
					}
					else if (params[0].getName().equals("java.sql.Timestamp"))
					{
						value = new java.sql.Timestamp(((java.util.Date) value).getTime());
					}
				}
			}

			// Don't call setter if the value object isn't the right type
//			if (this.isCompatibleType(value, params[0]))
//			{
//				setter.invoke(target, new Object[]{value});
//			}
//			else
//			{
//				throw new Exception(
//						"Cannot set " + prop.getName() + ": incompatible types.");
//			}

			value = convertCompatibleType(value, params[0]);
			setter.invoke(target, new Object[]{value});
		}
		catch (IllegalArgumentException e)
		{
			throw new Exception(
					"Cannot set " + prop.getName() + ": " + e.getMessage());

		}
		catch (IllegalAccessException e)
		{
			throw new Exception(
					"Cannot set " + prop.getName() + ": " + e.getMessage());

		}
		catch (InvocationTargetException e)
		{
			throw new Exception(
					"Cannot set " + prop.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * ResultSet.getObject() returns an Integer object for an INT column.  The
	 * setter method for the property might take an Integer or a primitive int.
	 * This method returns true if the value can be successfully passed into
	 * the setter method.  Remember, Method.invoke() handles the unwrapping
	 * of Integer into an int.
	 *
	 * @param value The value to be passed into the setter method.
	 * @param type  The setter's parameter type.
	 * @return boolean True if the value is compatible.
	 */
	private boolean isCompatibleType(Object value, Class<?> type)
	{
		// Do object check first, then primitives
		if (value == null || type.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Integer.TYPE) && Integer.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Long.TYPE) && Long.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Double.TYPE))
		{
			if(Double.class.isInstance(value) || Integer.class.isInstance(value) || Float.class.isInstance(value))
				return true;
//			else if(Integer.class.isInstance(value))
//			{
//				//value = (Double)((Number) value).doubleValue();
//				return true;
//			}

		}
		else if (type.equals(Float.TYPE) && Float.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Short.TYPE) && Short.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Byte.TYPE) && Byte.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Character.TYPE) && Character.class.isInstance(value))
		{
			return true;

		}
		else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value))
		{
			return true;

		}
		return false;

	}

	private Object convertCompatibleType(Object value, Class<?> type) throws IllegalArgumentException
	{
		// Do object check first, then primitives
		if (value == null || type.isInstance(value))
		{
			return value;
		}
		else if (type.equals(Character.TYPE) && Character.class.isInstance(value))
		{
			return value;
		}
		else if (type.equals(Boolean.TYPE) && Boolean.class.isInstance(value))
		{
			return value;

		}else if(Number.class.isInstance(value)){
			if (type.equals(Integer.TYPE) || type.equals(Integer.class))
			{
				return (Integer)((Number)value).intValue();
			}
			else if (type.equals(Long.TYPE) || type.equals(Long.class))
			{
				return (Long)((Number)value).longValue();
			}
			else if (type.equals(Double.TYPE) || type.equals(Double.class))
			{
				return (Double)((Number) value).doubleValue();
			}
			else if (type.equals(Float.TYPE) || type.equals(Float.class))
			{
				return (Float)((Number) value).floatValue();
			}
			else if (type.equals(Short.TYPE) || type.equals(Short.class))
			{
				return (Short)((Number) value).shortValue();
			}
			else if (type.equals(Byte.TYPE) || type.equals(Byte.class))
			{
				return (Byte)((Number) value).byteValue();
			}
		}
		
		throw new IllegalArgumentException("incompatible types.");
	}
	
	/**
	 * Factory method that returns a new instance of the given Class.  This
	 * is called at the start of the bean creation process and may be
	 * overridden to provide custom behavior like returning a cached bean
	 * instance.
	 *
	 * @param <T> The type of object to create
	 * @param c   The Class to create an object from.
	 * @return A newly created object of the Class.
	 * @throws Exception if creation failed.
	 */
	protected <T> T newInstance(Class<T> c) throws Exception
	{
		try
		{
			return c.newInstance();

		}
		catch (InstantiationException e)
		{
			throw new Exception(
					"Cannot create " + c.getName() + ": " + e.getMessage());

		}
		catch (IllegalAccessException e)
		{
			throw new Exception(
					"Cannot create " + c.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Returns a PropertyDescriptor[] for the given Class.
	 *
	 * @param c The Class to retrieve PropertyDescriptors for.
	 * @return A PropertyDescriptor[] describing the Class.
	 * @throws Exception if introspection failed.
	 */
	private PropertyDescriptor[] propertyDescriptors(Class<?> c)
			throws Exception
	{
		// Introspector caches BeanInfo classes for better performance
		BeanInfo beanInfo = null;
		try
		{
			beanInfo = Introspector.getBeanInfo(c);

		}
		catch (IntrospectionException e)
		{
			throw new Exception(
					"Bean introspection failed: " + e.getMessage());
		}

		return beanInfo.getPropertyDescriptors();
	}
}
