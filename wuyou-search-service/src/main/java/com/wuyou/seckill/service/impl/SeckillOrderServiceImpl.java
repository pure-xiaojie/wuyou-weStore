package com.wuyou.seckill.service.impl;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wuyou.mapper.TbSeckillGoodsMapper;
import com.wuyou.mapper.TbSeckillOrderMapper;
import com.wuyou.pojo.TbSeckillGoods;
import com.wuyou.pojo.TbSeckillOrder;
import com.wuyou.pojo.TbSeckillOrderExample;
import com.wuyou.pojo.TbSeckillOrderExample.Criteria;
import com.wuyou.seckill.service.SeckillOrderService;

import entity.PageResult;
import util.IdWorker;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

	@Autowired
	private TbSeckillOrderMapper seckillOrderMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillOrder> findAll() {
		return seckillOrderMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeckillOrder> page=   (Page<TbSeckillOrder>) seckillOrderMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeckillOrder seckillOrder) {
		seckillOrderMapper.insert(seckillOrder);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillOrder seckillOrder){
		seckillOrderMapper.updateByPrimaryKey(seckillOrder);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillOrder findOne(Long id){
		return seckillOrderMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			seckillOrderMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage2(TbSeckillOrder seckillOrder, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSeckillOrderExample example=new TbSeckillOrderExample();
		Criteria criteria = example.createCriteria();
		
		if(seckillOrder!=null){			
						if(seckillOrder.getUserId()!=null && seckillOrder.getUserId().length()>0){
				criteria.andUserIdLike("%"+seckillOrder.getUserId()+"%");
			}
			if(seckillOrder.getSellerId()!=null && seckillOrder.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seckillOrder.getSellerId()+"%");
			}
			if(seckillOrder.getStatus()!=null && seckillOrder.getStatus().length()>0){
				criteria.andStatusLike("%"+seckillOrder.getStatus()+"%");
			}
			if(seckillOrder.getReceiverAddress()!=null && seckillOrder.getReceiverAddress().length()>0){
				criteria.andReceiverAddressLike("%"+seckillOrder.getReceiverAddress()+"%");
			}
			if(seckillOrder.getReceiverMobile()!=null && seckillOrder.getReceiverMobile().length()>0){
				criteria.andReceiverMobileLike("%"+seckillOrder.getReceiverMobile()+"%");
			}
			if(seckillOrder.getReceiver()!=null && seckillOrder.getReceiver().length()>0){
				criteria.andReceiverLike("%"+seckillOrder.getReceiver()+"%");
			}
			if(seckillOrder.getTransactionId()!=null && seckillOrder.getTransactionId().length()>0){
				criteria.andTransactionIdLike("%"+seckillOrder.getTransactionId()+"%");
			}
	
		}
		
		Page<TbSeckillOrder> page= (Page<TbSeckillOrder>)seckillOrderMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}
		
		@Autowired
		private RedisTemplate redisTemplate;
		
		@Autowired
		private TbSeckillGoodsMapper seckillGoodsMapper;
		
		@Autowired
		private IdWorker idWorker;
			
		@Override
		public void submitOrder(Long seckillId, String userId) {
			//从缓存中查询秒杀商品		
			TbSeckillGoods seckillGoods =(TbSeckillGoods) redisTemplate.boundHashOps("seckillGoods").get(seckillId);
			if(seckillGoods==null){
				throw new RuntimeException("商品不存在");
			}
			if(seckillGoods.getStockCount()<=0){
				throw new RuntimeException("商品已抢购一空");
			}	
			//扣减（redis）库存		
			seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
			redisTemplate.boundHashOps("seckillGoods").put(seckillId, seckillGoods);//放回缓存
			if(seckillGoods.getStockCount()==0){//如果已经被秒光
				seckillGoodsMapper.updateByPrimaryKey(seckillGoods);//同步到数据库	
				redisTemplate.boundHashOps("seckillGoods").delete(seckillId);		
			}
			//保存（redis）订单(不存入数据库)
			long orderId = idWorker.nextId()/1000;
			TbSeckillOrder seckillOrder=new TbSeckillOrder();
			seckillOrder.setId(orderId);
			seckillOrder.setCreateTime(new Date());
			seckillOrder.setMoney(seckillGoods.getCostPrice());//秒杀价格
			seckillOrder.setSeckillId(seckillId);
			seckillOrder.setSellerId(seckillGoods.getSellerId());
			seckillOrder.setUserId(userId);//设置用户ID
			seckillOrder.setStatus("0");//状态
			redisTemplate.boundHashOps("seckillOrder").put(userId, seckillOrder);
			System.out.println("保存订单成功（redis）");
		}
	
}
