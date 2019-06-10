package com.wuyou.shop.controller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.wuyou.pojo.TbOrder;
import com.wuyou.pojo.TbOrderItem;
import com.wuyou.pojo.TbPayLog;

import com.wuyou.order.service.OrderService;

import entity.PageResult;
import entity.Result;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService orderService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){			
		List<TbOrder> selectAll =  orderService.findAll();
		
//		//遍历List集合
//		Iterator it = selectAll.iterator();
//		while(it.hasNext()) {
//		  TbOrder order = (TbOrder) it.next();
//		  System.out.println(order.getOrderId());
//		}
		return selectAll;
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return orderService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param order
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbOrder order){
		//获取当前登录人账号
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		order.setUserId(username);
		order.setSourceType("2");//订单来源  PC

		try {
			orderService.add(order);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbOrder findOne(Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			orderService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
	/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbOrder order, int page, int rows  ){
		return orderService.findPage2(order, page, rows);		
	}
	
	/**
	 * 查询支付日志
	 * @param userId
	 * @return
	 */
	@RequestMapping("/payLog")
	public TbPayLog searchPayLogFromRedis(String userId){
		return orderService.searchPayLogFromRedis(userId);		
	}
	
	/**
	 * 查询支付日志
	 * @param userId
	 * @return
	 */
	@RequestMapping("/orderDetail")
	public TbOrderItem find(Long orderId){
		return orderService.findOrderItem(orderId);		
	}
	
	/**
	 * 更新状态
	 * @param ids
	 * @param status
	 */
	@RequestMapping("/updateStatus")
	public Result updateStatus(Long[] ids, String status){		
		try {
			orderService.updateStatus(ids, status);
			return new Result(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "失败");
		}
	}
	
	/**
	 * 导出订单表
	 * 
	 */
	@RequestMapping("/export")
	public Result export() {
		List<TbOrder> order = findAll();
		
		String str[] = {" ", " ", "未发货","未发货","已发货","未评价","已评价"};
		//第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("订单列表");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        HSSFRow row0=sheet.createRow(0);//表头为0  
        HSSFCell cell0=row0.createCell(0);  
        cell0.setCellValue("订单列表");
        cell0.setCellStyle(style);
        //合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列  
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,6)); 
        
        HSSFRow row = sheet.createRow(1);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("订单id");
        cell.setCellStyle(style);
        cell = row.createCell((short) 1);
        cell.setCellValue("支付金额");
        cell.setCellStyle(style);
        cell = row.createCell((short) 2);
        cell.setCellValue("支付时间");
        cell.setCellStyle(style);
        cell = row.createCell((short) 3);
        cell.setCellValue("收件人");
        cell.setCellStyle(style);
        cell = row.createCell((short) 4);
        cell.setCellValue("收件地址");
        cell.setCellStyle(style);
        cell = row.createCell((short) 5);
        cell.setCellValue("联系方式");
        cell.setCellStyle(style);
        cell = row.createCell((short) 6);
        cell.setCellValue("状态");
        cell.setCellStyle(style);
        for(int i = 0;i < order.size();i ++){
            row = sheet.createRow((int) i + 2);
            TbOrder orders = order.get(i);
            row.createCell((short) 0).setCellValue(orders.getOrderId().toString());
            row.createCell((short) 1).setCellValue(orders.getPayment().toString());
            row.createCell((short) 2).setCellValue(orders.getPaymentTime().toString()+"元");
            row.createCell((short) 3).setCellValue(orders.getReceiver());
            row.createCell((short) 4).setCellValue(orders.getReceiverAreaName());
            row.createCell((short) 5).setCellValue(orders.getReceiverMobile());
            row.createCell((short) 6).setCellValue(str[Integer.parseInt(orders.getStatus())]);
        }
        FileOutputStream fout = null;
        boolean istrue = false;
		try {
			fout = new FileOutputStream("C:\\Users\\Public\\Desktop\\订单列表.xls");
			istrue=true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			wb.write(fout);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			fout.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(istrue == true) {
        	System.out.println("导出成功");
        	return new Result(true, "导出成功");
        } else {
        	return new Result(false, "导出成功");
        }
        
	}
}
