package meanai;

import java.util.ArrayList;

import com.springrts.ai.AICommand;
import com.springrts.ai.AICommandWrapper;
import com.springrts.ai.AICommand.Option;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.command.BuildUnitAICommand;
import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.UnitDef;

public class MeanAI extends AbstractOOAI 
{
	protected OOAICallback _callBack = null;
	protected UnitDefinitions _unitDefs = null;
	protected UnitManager _unitManager = null;
	protected Base _base = null;
	protected Harvesting _harvesting = null;
	
	public OOAICallback getCallBack() { return _callBack; }
	public UnitDefinitions getUnitDefs() { return _unitDefs; }
	public Base getBase() { return _base; }
	public Harvesting getHarvesting() { return _harvesting; }
	
	public MeanAI()
	{
		
	}
	
	@Override public int init(int teamId, OOAICallback callback) 
	{
		// save the engine callback
		_callBack = callback;
		_callBack.getLog().log("Alex AI started.");
		// create the unit definitions and parse them
		_unitDefs = new UnitDefinitions(this);
		_unitManager = new UnitManager(this);
		_base = new Base(this);
		_harvesting = new Harvesting(this);
		
		return 0;
	} 
	
	@Override public int update(int frame)
	{
		
		return 0;
	}
	
	@Override public int unitCreated(Unit unit, Unit builder)
	{
		return _unitManager.unitCreated(unit, builder);
	}
	
	@Override public int unitFinished(Unit unit)
	{
		return _unitManager.unitFinished(unit);
	}
	
	@Override public int unitIdle(Unit unit)
	{
		return _unitManager.unitIdle(unit);
	}
	
	@Override public int unitDestroyed(Unit unit, Unit attacker)
	{
		
		return 0;
	}
	
	public void log(String msg) 
	{
		_callBack.getLog().log(msg);
	}
}

