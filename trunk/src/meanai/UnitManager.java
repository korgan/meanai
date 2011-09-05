package meanai;

import com.springrts.ai.oo.Unit;

public class UnitManager
{
	protected MeanAI _core = null;
	protected Unit _commander = null;
	
	UnitManager(MeanAI core)
	{
		_core = core;
	}
	
	public int unitCreated(Unit unit, Unit builder)
	{
		String name = unit.getDef().getName();
		if (name.equals("armlab"))
			_core.getBase().setKbotLab(unit);
		else if (name.equals("armsolar"))
			_core.getHarvesting().addEnergy(unit);
		else if (name.equals("armmex"))
			_core.getHarvesting().addMetal(unit);
		return 0;
	}
	
	public int unitFinished(Unit unit)
	{
		String name = unit.getDef().getName();
		if (name.equals("armcom"))
		{
			_commander = unit;
			// set the position of the base
			_core.getBase().setBasePosition(unit.getPos());
			_core.getCallBack().getLog().log("Commander received.");
			
			unitIdle(unit);
		}
		return 0;
	}
	
	public int unitIdle(Unit unit)
	{
		String name = unit.getDef().getName();
		if (name.equals("armcom"))
		{
			if (_core.getBase().getPriority() > _core.getHarvesting().getPriority())
				_core.getBase().useUnit(unit);
			else
				_core.getHarvesting().useUnit(unit);
		}
		return 0;
	}
}
