/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.talend.dataquality.indicators.DistinctCountIndicator;
import org.talend.dataquality.indicators.IndicatorsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Distinct Count Indicator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.dataquality.indicators.impl.DistinctCountIndicatorImpl#getDistinctValueCount <em>Distinct Value Count</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class DistinctCountIndicatorImpl extends IndicatorImpl implements DistinctCountIndicator {

    /**
     * The default value of the '{@link #getDistinctValueCount() <em>Distinct Value Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDistinctValueCount()
     * @generated
     * @ordered
     */
    protected static final int DISTINCT_VALUE_COUNT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getDistinctValueCount() <em>Distinct Value Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDistinctValueCount()
     * @generated
     * @ordered
     */
    protected int distinctValueCount = DISTINCT_VALUE_COUNT_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected DistinctCountIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.DISTINCT_COUNT_INDICATOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Set<Object> getDistinctValues() {
        // TODO: implement this method
        // Ensure that you remove @generated or mark it @generated NOT
        throw new UnsupportedOperationException();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case IndicatorsPackage.DISTINCT_COUNT_INDICATOR__DISTINCT_VALUE_COUNT:
            return new Integer(getDistinctValueCount());
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case IndicatorsPackage.DISTINCT_COUNT_INDICATOR__DISTINCT_VALUE_COUNT:
            setDistinctValueCount(((Integer) newValue).intValue());
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case IndicatorsPackage.DISTINCT_COUNT_INDICATOR__DISTINCT_VALUE_COUNT:
            setDistinctValueCount(DISTINCT_VALUE_COUNT_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case IndicatorsPackage.DISTINCT_COUNT_INDICATOR__DISTINCT_VALUE_COUNT:
            return distinctValueCount != DISTINCT_VALUE_COUNT_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (distinctValueCount: ");
        result.append(distinctValueCount);
        result.append(')');
        return result.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getDistinctValueCount() {
        return distinctValueCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDistinctValueCount(int newDistinctValueCount) {
        int oldDistinctValueCount = distinctValueCount;
        distinctValueCount = newDistinctValueCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.DISTINCT_COUNT_INDICATOR__DISTINCT_VALUE_COUNT, oldDistinctValueCount, distinctValueCount));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.IndicatorImpl#storeSqlResults(java.lang.Object[])
     * 
     * ADDED scorreia 2008-04-30 storeSqlResults(List<Object[]> objects)
     */
    @Override
    public boolean storeSqlResults(List<Object[]> objects) {
        if (!super.checkResults(objects, 1)) {
            return false;
        }
        String c = String.valueOf(objects.get(0)[0]);
        this.setDistinctValueCount(Integer.valueOf(c));
        return true;
    }

} // DistinctCountIndicatorImpl
