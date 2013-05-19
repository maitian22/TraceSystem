package com.test.tracesystem.util;

import com.test.tracesystem.R;

public class Constant {

	/***
	 * 是否是debug状态，主要影响log打印
	 */
	public static boolean ISDEBUG = true;

	/***
	 * 左侧大菜单名称
	 */
	public static String[] parentMenu = { 
		"首页查询", 
		"物料溯源流程", 
		"生产溯源流程", 
		"销售溯源系统",
		"统计分析报表", 
		"基础信息设置", 
		"其他信息" };

	/***
	 * 左侧子菜单名称
	 */
	public static String[][] childMenu = {
			{ "正向原料跟踪", "正向跟踪报表", "反向产品溯源", "反向溯源报表" },
			{ "物料到货验收", "物料到货检验", "不合格物料处理", "物料入库管理" },
			{ "生产计划管理", "物料领料出库", "配料投料管理", "生产过程管理", "产品留样管理", "产品批次检验","产品入库管理","不合格产品销毁记录" },
			{ "产品发货管理", "产品退货管理", "产品召回管理", "不合格产品处理" },
			{ "物料入库统计", "物料存货总览", "产品出库统计", "产品库存总览" },
			{ "物料检测模板", "产品基础信息", "检测指标管理", "委托出厂检验登记表", "客户资料管理", "供应商资料管理","国家标准文件" },
			{ "学习计划","从业人员健康管理","消费者查询" } 
	};
	public static int hasGrandChildMenu(int groupPosition, int childPosition) {
		for (int i = 0; i < Constant.grandChildMenu.groupid.length; i++) {
			if (groupPosition == Constant.grandChildMenu.groupid[i]
					&& childPosition == Constant.grandChildMenu.childid[i]) {
				return i;
			}
		}
		return -1;
	}
	
	public static int hasSpecialAddTableDialog(String name){
		for(int i = 0;i<specialAddTableDialog.ChildMenu.length;i++)
			if(name.equals(specialAddTableDialog.ChildMenu[i]))
				return specialAddTableDialog.xmlid[i];
		return -1;
	}
	
	public static class specialAddTableDialog{
		public static String[] ChildMenu={"物料到货验收"};
		public static int[] xmlid={R.layout.product_check_out};
	//	public static specialxml[] xml= {
			//{"物料到货验收",product_check_out}
	//		};
	}
	
	public static class grandChildMenu{
		public static int[] groupid = {2,5,6,2};
		public static int[] childid = {3,5,0,1};
		public static String[] groupString = {
			"生产过程管理",
			"供应商资料管理",
			"学习计划",
			"物料领料出库"
		};
		public static String[][] grandChildMenu = {
			{"生产", "勾兑", "杀菌", "罐装", "包装"},
			{"供应商评价表", "合格供应商汇总表"},
			{"内部学习","外部学习"},
			{"领料单","采购物资出入库台帐"}
		};
		public static String[][] parentgrandChildMenu= {
			{"product", "blending","disinfect", "canning", "packaging"},
			{"supplierCommentformat","availableSupplierList"},
			{"internalStudy","externalStudy"},
			{"materialForm","MaterialinOrOutList"}
		};
	}

	public static String[][] parentTableName = {
			{ "positive_material_track", "positive_report_track","reverse_product_track", 
				"reverse_report_track" },
			{ "material_delivery_acceptance", "material_incoming_inspection",
					"unqualified_material_treatment",
					"meterial_storage_management" },
			{ "production_plan_management", "material_dispatching",
					"batch_feeding_management",
					"production_process_management",
					"retention_samples_management", "batch_test",
					"products_storage_management","unqualified_products_destroy_record" },
			{ "product_delivery_management", "product_return_management",
					"product_recall_management", "product_unqualified_process" },
			{ "material_storage_statistics", "meterial_inventory",
					"products_statistics", "product_inventory" },
			{ "material_detection_template", "product_information",
					"detection_target_management",
					"product_detection_template",
					"customer_information_management",
					"supplier_information_management", "national_standard_file" },
			{ "learning_plan", "empoyer_health_managerment","consumer_inquiries" } };

	/***
	 * 右侧设置启动activity的flag
	 */
	public static final int FLAG_START_ACTIVITY = 1000;

	public static final int FLAG_EXIT_ACTIVITY = 1001;
}