package com.secsc.entity;

/**
 * 
 * @Description 企业详细信息实体类
 * @author 吴俊杰
 * @Date 2017/07/12
 * 
 */
public class CompanyDetail {
	
	private String legalPersonCode;
	
	private String processUuid;
	
	private int years;
	
	private int employees;
	
	private int energyManager;

	private float industrialOutput;
	
	private float salesRevenue;
	
	private float payTaxes;
	
	private float eceQuivalent;
	
	private float eceQuivalence;
	
	private float productionCosts;
	
	private float energyCost;
	
	private double consumptionRatio;
	
	private double unitOuputEquivalent;
	
	private double unitOuputEquivalence;
	
	private double outputCost;
	
	private double energyComsumptionPUoIAV;
	
	private String preProcessMethod;

	public CompanyDetail() {
		super();
	}

	
	public CompanyDetail(String legalPersonCode, String processUuid, int years, int employees, int energyManager,
			float industrialOutput, float salesRevenue, float payTaxes, float eceQuivalent, float eceQuivalence,
			float productionCosts, float energyCost, double consumptionRatio, double unitOuputEquivalent,
			double unitOuputEquivalence, double outputCost, double energyComsumptionPUoIAV, String preProcessMethod) {
		super();
		this.legalPersonCode = legalPersonCode;
		this.processUuid = processUuid;
		this.years = years;
		this.employees = employees;
		this.energyManager = energyManager;
		this.industrialOutput = industrialOutput;
		this.salesRevenue = salesRevenue;
		this.payTaxes = payTaxes;
		this.eceQuivalent = eceQuivalent;
		this.eceQuivalence = eceQuivalence;
		this.productionCosts = productionCosts;
		this.energyCost = energyCost;
		this.consumptionRatio = consumptionRatio;
		this.unitOuputEquivalent = unitOuputEquivalent;
		this.unitOuputEquivalence = unitOuputEquivalence;
		this.outputCost = outputCost;
		this.energyComsumptionPUoIAV = energyComsumptionPUoIAV;
		this.preProcessMethod = preProcessMethod;
	}



	public CompanyDetail(String legalPersonCode, int years, int employees,
			int energyManager, float industrialOutput, float salesRevenue,
			float payTaxes, float eceQuivalent, float eceQuivalence,
			float productionCosts, float energyCost, double consumptionRatio,
			double unitOuputEquivalent, double unitOuputEquivalence,
			double energyComsumptionPUoIAV) {
		super();
		this.legalPersonCode = legalPersonCode;
		this.years = years;
		this.employees = employees;
		this.energyManager = energyManager;
		this.industrialOutput = industrialOutput;
		this.salesRevenue = salesRevenue;
		this.payTaxes = payTaxes;
		this.eceQuivalent = eceQuivalent;
		this.eceQuivalence = eceQuivalence;
		this.productionCosts = productionCosts;
		this.energyCost = energyCost;
		this.consumptionRatio = consumptionRatio;
		this.unitOuputEquivalent = unitOuputEquivalent;
		this.unitOuputEquivalence = unitOuputEquivalence;
		this.energyComsumptionPUoIAV = energyComsumptionPUoIAV;
	}

	/**
	 * @Description 获取法人单位代码
	 * @return legalPersonCode
	 */
	public String getLegalPersonCode() {
		return legalPersonCode;
	}

	/**
	 * @Description 获取年份
	 * @return years
	 */
	public int getYears() {
		return years;
	}

	/**
	 * @Description 获取从业人员
	 * @return employees
	 */
	public int getEmployees() {
		return employees;
	}

	/**
	 * @Description 获取能源管理师人数
	 * @return energyManager
	 */
	public int getEnergyManager() {
		return energyManager;
	}

	/**
	 * @Description 获取工业总产值
	 * @return industrialOutput
	 */
	public float getIndustrialOutput() {
		return industrialOutput;
	}

	/**
	 * @Description 获取销售收入
	 * @return salesRevenue
	 */
	public float getSalesRevenue() {
		return salesRevenue;
	}

	/**
	 * @Description 获取上缴利税
	 * @return payTaxes
	 */
	public float getPayTaxes() {
		return payTaxes;
	}

	/**
	 * @Description 获取综合能耗(当量值)
	 * @return eceQuivalent
	 */
	public float getEceQuivalent() {
		return eceQuivalent;
	}

	/**
	 * @Description 获取综合能耗(等价值)
	 * @return eceQuivalence
	 */
	public float getEceQuivalence() {
		return eceQuivalence;
	}

	/**
	 * @Description 获取生产成本
	 * @return productionCosts
	 */
	public float getProductionCosts() {
		return productionCosts;
	}

	/**
	 * @Description 获取能源消费成本
	 * @return energyCost
	 */
	public float getEnergyCost() {
		return energyCost;
	}

	/**
	 * @Description 获取能源消费占成本比例
	 * @return consumptionRatio
	 */
	public double getConsumptionRatio() {
		return consumptionRatio;
	}

	/**
	 * @Description 获取单位产值综合能耗(当量值)
	 * @return unitOuputEquivalent
	 */
	public double getUnitOuputEquivalent() {
		return unitOuputEquivalent;
	}

	/**
	 * @Description 获取单位产值综合能耗(等价值)
	 * @return unitOuputEquivalence
	 */
	public double getUnitOuputEquivalence() {
		return unitOuputEquivalence;
	}

	/**
	 * @Description 设置法人单位代码
	 * @param legalPersonCode
	 */
	public void setLegalPersonCode(String legalPersonCode) {
		this.legalPersonCode = legalPersonCode;
	}

	/**
	 * @Description 设置年份
	 * @param years
	 */
	public void setYears(int years) {
		this.years = years;
	}

	/**
	 * @Description 设置从业人员
	 * @param employees
	 */
	public void setEmployees(int employees) {
		this.employees = employees;
	}

	/**
	 * @Description 设置能源管理师人数
	 * @param energyManager
	 */
	public void setEnergyManager(int energyManager) {
		this.energyManager = energyManager;
	}

	/**
	 * @Description 设置工业总产值
	 * @param industrialOutput
	 */
	public void setIndustrialOutput(float industrialOutput) {
		this.industrialOutput = industrialOutput;
	}

	/**
	 * @Description 设置销售收入
	 * @param salesRevenue
	 */
	public void setSalesRevenue(float salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	/**
	 * @Description 设置上缴利税
	 * @param payTaxes
	 */
	public void setPayTaxes(float payTaxes) {
		this.payTaxes = payTaxes;
	}

	/**
	 * @Description 设置综合能耗(当量值)
	 * @param eceQuivalent
	 */
	public void setEceQuivalent(float eceQuivalent) {
		this.eceQuivalent = eceQuivalent;
	}

	/**
	 * @Description 设置综合能耗(等价值)
	 * @param eceQuivalence
	 */
	public void setEceQuivalence(float eceQuivalence) {
		this.eceQuivalence = eceQuivalence;
	}

	/**
	 * @Description 设置生产成本
	 * @param productionCosts
	 */
	public void setProductionCosts(float productionCosts) {
		this.productionCosts = productionCosts;
	}

	/**
	 * @Description 设置能源消费成本
	 * @param energyCost
	 */
	public void setEnergyCost(float energyCost) {
		this.energyCost = energyCost;
	}

	/**
	 * @Description 设置能源消费占成本比例
	 * @param consumptionRatio
	 */
	public void setConsumptionRatio(double consumptionRatio) {
		this.consumptionRatio = consumptionRatio;
	}

	/**
	 * @Description 设置单位产值综合能耗(当量值)
	 * @param unitOuputEquivalent
	 */
	public void setUnitOuputEquivalent(double unitOuputEquivalent) {
		this.unitOuputEquivalent = unitOuputEquivalent;
	}

	/**
	 * @Description 设置单位产值综合能耗(等价值)
	 * @param unitOuputEquivalence
	 */
	public void setUnitOuputEquivalence(double unitOuputEquivalence) {
		this.unitOuputEquivalence = unitOuputEquivalence;
	}

	/**
	 * @Description 获取产值-成本
	 * @return outputCost
	 */
	public double getOutputCost() {
		return outputCost;
	}

	/**
	 * @Description 设置产值-成本
	 * @param outputCost
	 */
	public void setOutputCost(double outputCost) {
		this.outputCost = outputCost;
	}

	/**
	 * @Description 获取预处理唯一标识
	 * @return processUuid
	 */
	public String getProcessUuid() {
		return processUuid;
	}

	/**
	 * @Description 设置预处理唯一标识
	 * @param processUuid
	 */
	public void setProcessUuid(String processUuid) {
		this.processUuid = processUuid;
	}

	public double getEnergyComsumptionPUoIAV() {
		return energyComsumptionPUoIAV;
	}

	public void setEnergyComsumptionPUoIAV(double energyComsumptionPUoIAV) {
		this.energyComsumptionPUoIAV = energyComsumptionPUoIAV;
	}


	public String getPreProcessMethod() {
		return preProcessMethod;
	}


	public void setPreProcessMethod(String preProcessMethod) {
		this.preProcessMethod = preProcessMethod;
	}

	

	
}