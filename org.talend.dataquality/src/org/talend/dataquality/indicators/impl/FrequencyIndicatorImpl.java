/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.dataquality.indicators.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.talend.dataquality.helpers.IndicatorDocumentationHandler;
import org.talend.dataquality.indicators.DistinctCountIndicator;
import org.talend.dataquality.indicators.DuplicateCountIndicator;
import org.talend.dataquality.indicators.FrequencyIndicator;
import org.talend.dataquality.indicators.IndicatorsPackage;
import org.talend.dataquality.indicators.ModeIndicator;
import org.talend.dataquality.indicators.UniqueCountIndicator;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Frequency Indicator</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getUniqueValues <em>Unique Values</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getDistinctValueCount <em>Distinct Value Count</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getUniqueValueCount <em>Unique Value Count</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getDuplicateValueCount <em>Duplicate Value Count</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getValueToFreq <em>Value To Freq</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getModeIndicator <em>Mode Indicator</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getDistinctCountIndicator <em>Distinct Count Indicator</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getUniqueCountIndicator <em>Unique Count Indicator</em>}</li>
 * <li>{@link org.talend.dataquality.indicators.impl.FrequencyIndicatorImpl#getDuplicateCountIndicator <em>Duplicate Count Indicator</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class FrequencyIndicatorImpl extends CompositeIndicatorImpl implements FrequencyIndicator {

    private static Logger log = Logger.getLogger(FrequencyIndicatorImpl.class);

    /**
     * The cached value of the '{@link #getUniqueValues() <em>Unique Values</em>}' attribute list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getUniqueValues()
     * @generated
     * @ordered
     */
    protected EList<Object> uniqueValues;

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
     * The default value of the '{@link #getUniqueValueCount() <em>Unique Value Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getUniqueValueCount()
     * @generated
     * @ordered
     */
    protected static final int UNIQUE_VALUE_COUNT_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getUniqueValueCount() <em>Unique Value Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getUniqueValueCount()
     * @generated
     * @ordered
     */
    protected int uniqueValueCount = UNIQUE_VALUE_COUNT_EDEFAULT;

    /**
     * The default value of the '{@link #getDuplicateValueCount() <em>Duplicate Value Count</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDuplicateValueCount()
     * @generated
     * @ordered
     */
    protected static final int DUPLICATE_VALUE_COUNT_EDEFAULT = 0;

    /**
     * The default value of the '{@link #getValueToFreq() <em>Value To Freq</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getValueToFreq()
     * @generated NOT
     * @ordered
     */
    protected static final HashMap<Object, Long> VALUE_TO_FREQ_EDEFAULT = new HashMap<Object, Long>();

    /**
     * The cached value of the '{@link #getValueToFreq() <em>Value To Freq</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getValueToFreq()
     * @generated
     * @ordered
     */
    protected HashMap<Object, Long> valueToFreq = VALUE_TO_FREQ_EDEFAULT;

    /**
     * The cached value of the '{@link #getModeIndicator() <em>Mode Indicator</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getModeIndicator()
     * @generated
     * @ordered
     */
    protected ModeIndicator modeIndicator;

    /**
     * The cached value of the '{@link #getDistinctCountIndicator() <em>Distinct Count Indicator</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDistinctCountIndicator()
     * @generated
     * @ordered
     */
    protected DistinctCountIndicator distinctCountIndicator;

    /**
     * The cached value of the '{@link #getUniqueCountIndicator() <em>Unique Count Indicator</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getUniqueCountIndicator()
     * @generated
     * @ordered
     */
    protected UniqueCountIndicator uniqueCountIndicator;

    /**
     * The cached value of the '{@link #getDuplicateCountIndicator() <em>Duplicate Count Indicator</em>}' containment
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getDuplicateCountIndicator()
     * @generated
     * @ordered
     */
    protected DuplicateCountIndicator duplicateCountIndicator;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected FrequencyIndicatorImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return IndicatorsPackage.Literals.FREQUENCY_INDICATOR;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList<Object> getUniqueValues() {
        if (uniqueValues == null) {
            uniqueValues = new EDataTypeUniqueEList<Object>(Object.class, this,
                    IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUES);
        }
        return uniqueValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public long getCount(Object dataValue) {
        Long freq = this.valueToFreq.get(dataValue);
        return (freq == null) ? 0L : freq;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public double getFrequency(Object dataValue) {
        if (this.count == 0L) {
            return 0.0d;
        }
        return getCount(dataValue) / this.count;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public Set<Object> getDistinctValues() {
        if (!distinctComputed) {
            distinctValues = computeDistinctValues();
        }
        return distinctValues;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR:
            return basicSetModeIndicator(null, msgs);
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR:
            return basicSetDistinctCountIndicator(null, msgs);
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR:
            return basicSetUniqueCountIndicator(null, msgs);
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR:
            return basicSetDuplicateCountIndicator(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * ADDED scorreia Method "computeDistinctValues".
     * 
     * @return the distinct values
     */
    private Set<Object> computeDistinctValues() {
        Set<Object> keySet = this.valueToFreq.keySet();
        this.setDistinctValueCount(keySet.size());
        distinctComputed = true;
        return keySet;
    }

    private Set<Object> distinctValues = null;

    /**
     * @generated
     */
    protected int getDistinctValueCountGen() {
        return distinctValueCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public int getDistinctValueCount() {
        if (!distinctComputed) {
            computeDistinctValues();
        }
        return getDistinctValueCountGen();
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
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_VALUE_COUNT,
                    oldDistinctValueCount, distinctValueCount));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getUniqueValueCount() {
        return uniqueValueCount;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setUniqueValueCount(int newUniqueValueCount) {
        int oldUniqueValueCount = uniqueValueCount;
        uniqueValueCount = newUniqueValueCount;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUE_COUNT,
                    oldUniqueValueCount, uniqueValueCount));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public int getDuplicateValueCount() {
        return (int) (count - getUniqueValueCount()); // CAST in int
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public HashMap<Object, Long> getValueToFreqGen() {
        return valueToFreq;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.dataquality.indicators.FrequencyIndicator#getValueToFreq() @generated NOT
     */
    public HashMap<Object, Long> getValueToFreq() {
        if (valueToFreq == VALUE_TO_FREQ_EDEFAULT) {
            valueToFreq = new HashMap<Object, Long>();
        }
        return getValueToFreqGen();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setValueToFreq(HashMap<Object, Long> newValueToFreq) {
        HashMap<Object, Long> oldValueToFreq = valueToFreq;
        valueToFreq = newValueToFreq;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.FREQUENCY_INDICATOR__VALUE_TO_FREQ,
                    oldValueToFreq, valueToFreq));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ModeIndicator getModeIndicator() {
        return modeIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetModeIndicator(ModeIndicator newModeIndicator, NotificationChain msgs) {
        ModeIndicator oldModeIndicator = modeIndicator;
        modeIndicator = newModeIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR, oldModeIndicator, newModeIndicator);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setModeIndicator(ModeIndicator newModeIndicator) {
        if (newModeIndicator != modeIndicator) {
            NotificationChain msgs = null;
            if (modeIndicator != null)
                msgs = ((InternalEObject) modeIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR, null, msgs);
            if (newModeIndicator != null)
                msgs = ((InternalEObject) newModeIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR, null, msgs);
            msgs = basicSetModeIndicator(newModeIndicator, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR,
                    newModeIndicator, newModeIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DistinctCountIndicator getDistinctCountIndicator() {
        return distinctCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetDistinctCountIndicator(DistinctCountIndicator newDistinctCountIndicator,
            NotificationChain msgs) {
        DistinctCountIndicator oldDistinctCountIndicator = distinctCountIndicator;
        distinctCountIndicator = newDistinctCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR, oldDistinctCountIndicator,
                    newDistinctCountIndicator);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDistinctCountIndicator(DistinctCountIndicator newDistinctCountIndicator) {
        if (newDistinctCountIndicator != distinctCountIndicator) {
            NotificationChain msgs = null;
            if (distinctCountIndicator != null)
                msgs = ((InternalEObject) distinctCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR, null, msgs);
            if (newDistinctCountIndicator != null)
                msgs = ((InternalEObject) newDistinctCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR, null, msgs);
            msgs = basicSetDistinctCountIndicator(newDistinctCountIndicator, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR, newDistinctCountIndicator,
                    newDistinctCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public UniqueCountIndicator getUniqueCountIndicator() {
        return uniqueCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetUniqueCountIndicator(UniqueCountIndicator newUniqueCountIndicator, NotificationChain msgs) {
        UniqueCountIndicator oldUniqueCountIndicator = uniqueCountIndicator;
        uniqueCountIndicator = newUniqueCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR, oldUniqueCountIndicator,
                    newUniqueCountIndicator);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setUniqueCountIndicator(UniqueCountIndicator newUniqueCountIndicator) {
        if (newUniqueCountIndicator != uniqueCountIndicator) {
            NotificationChain msgs = null;
            if (uniqueCountIndicator != null)
                msgs = ((InternalEObject) uniqueCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR, null, msgs);
            if (newUniqueCountIndicator != null)
                msgs = ((InternalEObject) newUniqueCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR, null, msgs);
            msgs = basicSetUniqueCountIndicator(newUniqueCountIndicator, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR,
                    newUniqueCountIndicator, newUniqueCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public DuplicateCountIndicator getDuplicateCountIndicator() {
        return duplicateCountIndicator;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetDuplicateCountIndicator(DuplicateCountIndicator newDuplicateCountIndicator,
            NotificationChain msgs) {
        DuplicateCountIndicator oldDuplicateCountIndicator = duplicateCountIndicator;
        duplicateCountIndicator = newDuplicateCountIndicator;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR, oldDuplicateCountIndicator,
                    newDuplicateCountIndicator);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDuplicateCountIndicator(DuplicateCountIndicator newDuplicateCountIndicator) {
        if (newDuplicateCountIndicator != duplicateCountIndicator) {
            NotificationChain msgs = null;
            if (duplicateCountIndicator != null)
                msgs = ((InternalEObject) duplicateCountIndicator).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR, null, msgs);
            if (newDuplicateCountIndicator != null)
                msgs = ((InternalEObject) newDuplicateCountIndicator).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
                        - IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR, null, msgs);
            msgs = basicSetDuplicateCountIndicator(newDuplicateCountIndicator, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET,
                    IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR, newDuplicateCountIndicator,
                    newDuplicateCountIndicator));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUES:
            return getUniqueValues();
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_VALUE_COUNT:
            return new Integer(getDistinctValueCount());
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUE_COUNT:
            return new Integer(getUniqueValueCount());
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_VALUE_COUNT:
            return new Integer(getDuplicateValueCount());
        case IndicatorsPackage.FREQUENCY_INDICATOR__VALUE_TO_FREQ:
            return getValueToFreq();
        case IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR:
            return getModeIndicator();
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR:
            return getDistinctCountIndicator();
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR:
            return getUniqueCountIndicator();
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR:
            return getDuplicateCountIndicator();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUES:
            getUniqueValues().clear();
            getUniqueValues().addAll((Collection<? extends Object>) newValue);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_VALUE_COUNT:
            setDistinctValueCount(((Integer) newValue).intValue());
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUE_COUNT:
            setUniqueValueCount(((Integer) newValue).intValue());
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__VALUE_TO_FREQ:
            setValueToFreq((HashMap<Object, Long>) newValue);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR:
            setModeIndicator((ModeIndicator) newValue);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR:
            setDistinctCountIndicator((DistinctCountIndicator) newValue);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR:
            setUniqueCountIndicator((UniqueCountIndicator) newValue);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR:
            setDuplicateCountIndicator((DuplicateCountIndicator) newValue);
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
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUES:
            getUniqueValues().clear();
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_VALUE_COUNT:
            setDistinctValueCount(DISTINCT_VALUE_COUNT_EDEFAULT);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUE_COUNT:
            setUniqueValueCount(UNIQUE_VALUE_COUNT_EDEFAULT);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__VALUE_TO_FREQ:
            setValueToFreq(VALUE_TO_FREQ_EDEFAULT);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR:
            setModeIndicator((ModeIndicator) null);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR:
            setDistinctCountIndicator((DistinctCountIndicator) null);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR:
            setUniqueCountIndicator((UniqueCountIndicator) null);
            return;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR:
            setDuplicateCountIndicator((DuplicateCountIndicator) null);
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
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUES:
            return uniqueValues != null && !uniqueValues.isEmpty();
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_VALUE_COUNT:
            return distinctValueCount != DISTINCT_VALUE_COUNT_EDEFAULT;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_VALUE_COUNT:
            return uniqueValueCount != UNIQUE_VALUE_COUNT_EDEFAULT;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_VALUE_COUNT:
            return getDuplicateValueCount() != DUPLICATE_VALUE_COUNT_EDEFAULT;
        case IndicatorsPackage.FREQUENCY_INDICATOR__VALUE_TO_FREQ:
            return VALUE_TO_FREQ_EDEFAULT == null ? valueToFreq != null : !VALUE_TO_FREQ_EDEFAULT.equals(valueToFreq);
        case IndicatorsPackage.FREQUENCY_INDICATOR__MODE_INDICATOR:
            return modeIndicator != null;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DISTINCT_COUNT_INDICATOR:
            return distinctCountIndicator != null;
        case IndicatorsPackage.FREQUENCY_INDICATOR__UNIQUE_COUNT_INDICATOR:
            return uniqueCountIndicator != null;
        case IndicatorsPackage.FREQUENCY_INDICATOR__DUPLICATE_COUNT_INDICATOR:
            return duplicateCountIndicator != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * true is distinct value count is computed
     */
    private boolean distinctComputed = false;

    @Override
    public boolean handle(Object data) {
        super.handle(data);
        Long freq = getValueToFreq().get(data);
        if (freq == null) { // new data
            freq = 0L;
            this.getUniqueValues().add(data);
            this.uniqueValueCount++;
        } else { // data not new
            this.getUniqueValues().remove(data);
            if (freq.compareTo(1L) == 0) { // decrement when data is seen twice
                this.uniqueValueCount--;
            }
        }
        freq++;
        // TODO scorreia compute distinct values ?
        valueToFreq.put(data, freq);
        return freq > 0;

    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer(IndicatorDocumentationHandler.getName(this.eClass().getClassifierID()));
        Set<Object> keySet = this.valueToFreq.keySet();
        for (Object object : keySet) {
            buf.append(object);
            buf.append(": ");
            buf.append(this.valueToFreq.get(object));
            buf.append("\n");
        }
        // TODO scorreia could compute average frequency (sum values/# keys)
        return buf.toString();
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
        if (!checkResults(objects, 2)) {
            return false;
        }
        HashMap<Object, Long> mapVal2Freq = new HashMap<Object, Long>();

        for (Object[] value2freq : objects) {
            if (value2freq.length != 2) {
                log.error("Problem with result for Frequency indicator");
                return false;
            }
            Object value = value2freq[0];
            Long freq = (Long) value2freq[1];
            mapVal2Freq.put(value, freq);
        }
        this.setValueToFreq(mapVal2Freq);
        return true;
    }

} // FrequencyIndicatorImpl
