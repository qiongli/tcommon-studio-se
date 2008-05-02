/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.talend.dataquality.indicators.AverageLengthIndicator;
import org.talend.dataquality.indicators.IndicatorsPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Average Length Indicator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.dataquality.indicators.impl.AverageLengthIndicatorImpl#getSumLength <em>Sum Length</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class AverageLengthIndicatorImpl extends LengthIndicatorImpl implements AverageLengthIndicator {

    private static Logger log = Logger.getLogger(AverageLengthIndicatorImpl.class);

    /**
     * The default value of the '{@link #getSumLength() <em>Sum Length</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getSumLength()
     * @generated
     * @ordered
     */
    protected static final long SUM_LENGTH_EDEFAULT = 0L;

    /**
     * The cached value of the '{@link #getSumLength() <em>Sum Length</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getSumLength()
     * @generated
     * @ordered
     */
    protected long sumLength = SUM_LENGTH_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected AverageLengthIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.AVERAGE_LENGTH_INDICATOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public long getSumLength() {
        return sumLength;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setSumLength(long newSumLength) {
        long oldSumLength = sumLength;
        sumLength = newSumLength;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.AVERAGE_LENGTH_INDICATOR__SUM_LENGTH,
                    oldSumLength, sumLength));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public double getAverageLength() {
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
        case IndicatorsPackage.AVERAGE_LENGTH_INDICATOR__SUM_LENGTH:
            return new Long(getSumLength());
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
        case IndicatorsPackage.AVERAGE_LENGTH_INDICATOR__SUM_LENGTH:
            setSumLength(((Long) newValue).longValue());
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
        case IndicatorsPackage.AVERAGE_LENGTH_INDICATOR__SUM_LENGTH:
            setSumLength(SUM_LENGTH_EDEFAULT);
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
        case IndicatorsPackage.AVERAGE_LENGTH_INDICATOR__SUM_LENGTH:
            return sumLength != SUM_LENGTH_EDEFAULT;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /*
     * (non-Javadoc) ADDED scorreia 2008-04-08 compute sum
     * 
     * @see org.talend.dataquality.indicators.impl.IndicatorImpl#handle(java.lang.Object)
     */
    @Override
    public boolean handle(Object data) {
        boolean ok = super.handle(data);
        if (data != null) {
            String str = (String) data;
            sumLength += str.length();
        }
        // TODO scorreia handle case when data is null and should be replaced by empty string
        return ok;
    }

    /*
     * (non-Javadoc) ADDED scorreia 2008-04-08 get average length
     * 
     * @see org.talend.dataquality.indicators.impl.LengthIndicatorImpl#getLength()
     */
    @Override
    public int getLength() {
        if (count == 0) {
            return 0;
        }
        // else
        return (int) (sumLength / count); // CAST in int
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.impl.LengthIndicatorImpl#storeSqlResults(java.lang.Object[]) ADDED
     * scorreia 2008-04-30 storeSqlResults(List<Object[]> objects)
     */
    @Override
    public boolean storeSqlResults(List<Object[]> objects) {

        if (!checkResults(objects, 2)) {
            return false;
        }
        this.setCount((Integer) objects.get(0)[1]);
        this.setSumLength((Long) objects.get(0)[0]);
        return true;
    }

} // AverageLengthIndicatorImpl
