package meanai;

import java.util.ArrayList;

import com.springrts.ai.AICommand;
import com.springrts.ai.AICommandWrapper;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.command.BuildUnitAICommand;
import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.UnitDef;

public class Base
{
	protected MeanAI _core;
	protected AIFloat3 _position = null;
	/**
	 * The level of this base.
	 */
	protected int _level = 1;
	/**
	 * The K-Bot lab.
	 */
	protected Unit _kbotLab = null;
	
	public void setKbotLab(Unit unit) { _kbotLab = unit; }
	public void setBasePosition(AIFloat3 basePosition) { _position = basePosition; }
	
	public Base(MeanAI core)
	{
		_core = core;
	}
	
	public int useUnit(Unit unit)
	{
		if (unit.getDef().getName().equals("armcom"))
		{
			if (_kbotLab == null)
			{
				UnitDef labDef = _core.getUnitDefs()._kbot1Lab;
				AIFloat3 position = _core.getCallBack().getMap().findClosestBuildSite(labDef, _position, 1000, 10, 0);
				AICommand command = new BuildUnitAICommand(unit, -1, new ArrayList<AICommand.Option>(), 10000, labDef, position, 0);
				_core.getCallBack().getEngine().handleCommand(AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
				_core.log("Commander build a KBot lab.");
			}
		}
		return 0;
	}
	
	public int getPriority()
	{
		if (_kbotLab == null)
		{
			return 18;
		}
		else 
		{
			return 0;
		}
	}
}
