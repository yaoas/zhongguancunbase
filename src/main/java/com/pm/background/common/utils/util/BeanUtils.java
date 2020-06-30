package com.pm.background.common.utils.util;
import com.pm.background.common.utils.ObjectUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;


/**
 * @description 进行对象操作的工具类
 * @author : Larry
 * @date : 2020/4/17 09:25
 * @see  org.apache.commons.beanutils.BeanUtils
 常用方法:
 * 1、copyPeoperties：复制属性值，从一个对象到另一个对象
 *
 * 2、getProperty：获取对象属性值
 *
 * 3、setProperty：设置对象属性值
 *
 * 4、populate：根据Map给属性复制
 *
 * 5、copyPeoperty：复制单个值，从一个对象到另一个对象
 *
 * 6、cloneBean：克隆bean实例
 *
 */

public class BeanUtils {

    @Deprecated
    private static int debug = 0;

    /**
     * The <code>debug</code> static property is no longer used
     * @return debug property
     * @deprecated BeanUtils now uses commons-logging for all log messages.
     *             Use your favorite logging tool to configure logging for
     *             this class.
     */
    @Deprecated
    public static int getDebug() {
        return (debug);
    }

    /**
     * The <code>debug</code> static property is no longer used
     * @param newDebug debug property
     * @deprecated BeanUtils now uses commons-logging for all log messages.
     *             Use your favorite logging tool to configure logging for
     *             this class.
     */
    @Deprecated
    public static void setDebug(final int newDebug) {
        debug = newDebug;
    }

    // --------------------------------------------------------- Class Methods

    /**
     * <p>Clone a bean based on the available property getters and setters,
     * even if the bean class itself does not implement Cloneable.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean to be cloned
     * @return the cloned bean
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InstantiationException if a new instance of the bean's
     *  class cannot be instantiated
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#cloneBean
     */
    public static Object cloneBean(final Object bean)
            throws IllegalAccessException, InstantiationException,
            InvocationTargetException, NoSuchMethodException {

        return BeanUtilsBean.getInstance().cloneBean(bean);

    }


    /**
     * <p>Copy property values from the origin bean to the destination bean
     * for all cases where the property names are the same.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param dest Destination bean whose properties are modified
     * @param orig Origin bean whose properties are retrieved
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws IllegalArgumentException if the <code>dest</code> or
     *  <code>orig</code> argument is null or if the <code>dest</code>
     *  property type is different from the source type and the relevant
     *  converter has not been registered.
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#copyProperties
     */
    public static void copyProperties(final Object dest, final Object orig)
            throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }


    /**
     * <p>Copy the specified property value to the specified destination bean,
     * performing any type conversion that is required.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean on which setting is to be performed
     * @param name Property name (can be nested/indexed/mapped/combo)
     * @param value Value to be set
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#copyProperty
     */
    public static void copyProperty(final Object bean, final String name, final Object value)
            throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().copyProperty(bean, name, value);
    }


    /**
     * <p>Return the entire set of properties for which the specified bean
     * provides a read method.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose properties are to be extracted
     * @return Map of property descriptors
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#describe
     */
    public static Map<String, String> describe(final Object bean)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().describe(bean);
    }


    /**
     * <p>Return the value of the specified array property of the specified
     * bean, as a String array.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Name of the property to be extracted
     * @return The array property value
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getArrayProperty
     */
    public static String[] getArrayProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getArrayProperty(bean, name);
    }


    /**
     * <p>Return the value of the specified indexed property of the specified
     * bean, as a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name <code>propertyname[index]</code> of the property value
     *  to be extracted
     * @return The indexed property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getIndexedProperty(Object, String)
     */
    public static String getIndexedProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getIndexedProperty(bean, name);

    }


    /**
     * Return the value of the specified indexed property of the specified
     * bean, as a String.  The index is specified as a method parameter and
     * must *not* be included in the property name expression
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Simple property name of the property value to be extracted
     * @param index Index of the property value to be extracted
     * @return The indexed property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getIndexedProperty(Object, String, int)
     */
    public static String getIndexedProperty(final Object bean,
                                            final String name, final int index)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getIndexedProperty(bean, name, index);

    }


    /**
     * </p>Return the value of the specified indexed property of the specified
     * bean, as a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name <code>propertyname(index)</code> of the property value
     *  to be extracted
     * @return The mapped property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getMappedProperty(Object, String)
     */
    public static String getMappedProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getMappedProperty(bean, name);

    }


    /**
     * </p>Return the value of the specified mapped property of the specified
     * bean, as a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Simple property name of the property value to be extracted
     * @param key Lookup key of the property value to be extracted
     * @return The mapped property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getMappedProperty(Object, String, String)
     */
    public static String getMappedProperty(final Object bean,
                                           final String name, final String key)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getMappedProperty(bean, name, key);

    }


    /**
     * <p>Return the value of the (possibly nested) property of the specified
     * name, for the specified bean, as a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Possibly nested name of the property to be extracted
     * @return The nested property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws IllegalArgumentException if a nested reference to a
     *  property returns null
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getNestedProperty
     */
    public static String getNestedProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getNestedProperty(bean, name);

    }


    /**
     * <p>Return the value of the specified property of the specified bean,
     * no matter which property reference format is used, as a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Possibly indexed and/or nested name of the property
     *  to be extracted
     * @return The property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getProperty
     */
    public static String getProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getProperty(bean, name);

    }


    /**
     * <p>Return the value of the specified simple property of the specified
     * bean, converted to a String.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean whose property is to be extracted
     * @param name Name of the property to be extracted
     * @return The property's value, converted to a String
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @throws NoSuchMethodException if an accessor method for this
     *  property cannot be found
     * @see BeanUtilsBean#getSimpleProperty
     */
    public static String getSimpleProperty(final Object bean, final String name)
            throws IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {

        return BeanUtilsBean.getInstance().getSimpleProperty(bean, name);

    }


    /**
     * <p>Populate the JavaBeans properties of the specified bean, based on
     * the specified name/value pairs.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean JavaBean whose properties are being populated
     * @param properties Map keyed by property name, with the
     *  corresponding (String or String[]) value(s) to be set
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#populate
     */
    public static void populate(final Object bean, final Map<String, ? extends Object> properties)
            throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().populate(bean, properties);
    }


    /**
     * <p>Set the specified property value, performing type conversions as
     * required to conform to the type of the destination property.</p>
     *
     * <p>For more details see <code>BeanUtilsBean</code>.</p>
     *
     * @param bean Bean on which setting is to be performed
     * @param name Property name (can be nested/indexed/mapped/combo)
     * @param value Value to be set
     *
     * @throws IllegalAccessException if the caller does not have
     *  access to the property accessor method
     * @throws InvocationTargetException if the property accessor method
     *  throws an exception
     * @see BeanUtilsBean#setProperty
     */
    public static void setProperty(final Object bean, final String name, final Object value)
            throws IllegalAccessException, InvocationTargetException {

        BeanUtilsBean.getInstance().setProperty(bean, name, value);
    }

    /**
     * If we're running on JDK 1.4 or later, initialize the cause for the given throwable.
     *
     * @param  throwable The throwable.
     * @param  cause     The cause of the throwable.
     * @return  true if the cause was initialized, otherwise false.
     * @since 1.8.0
     */
    public static boolean initCause(final Throwable throwable, final Throwable cause) {
        return BeanUtilsBean.getInstance().initCause(throwable, cause);
    }

    /**
     * Create a cache.
     * @param <K> the key type of the cache
     * @param <V> the value type of the cache
     * @return a new cache
     * @since 1.8.0
     */
}
