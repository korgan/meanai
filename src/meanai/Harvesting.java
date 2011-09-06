package meanai;

import java.util.ArrayList;
import java.util.List;

import com.springrts.ai.AICommand;
import com.springrts.ai.AICommandWrapper;
import com.springrts.ai.AIFloat3;
import com.springrts.ai.command.BuildUnitAICommand;
import com.springrts.ai.oo.Resource;
import com.springrts.ai.oo.Unit;
import com.springrts.ai.oo.UnitDef;

public class Harvesting
{
	protected MeanAI _core;
	ArrayList<Unit> _metal;
	ArrayList<Unit> _energy;
	
	Resource _resourceMetal;
	Resource _resourceEnergy;
	List<AIFloat3> _metalSpots;
	float _harvesterRadius;
	
	/**
	 * 
	 * @param core
	 */
	Harvesting(MeanAI core)
	{
		_core = core;
		_metal = new ArrayList<Unit>();
		_energy = new ArrayList<Unit>();
		
		_resourceMetal = Resource.getInstance(_core.getCallBack(), 0);
		_resourceEnergy = Resource.getInstance(_core.getCallBack(), 1);
		_metalSpots = _core.getCallBack().getMap().getResourceMapSpotsPositions(_resourceMetal);
		_harvesterRadius = _core.getCallBack().getMap().getExtractorRadius(_resourceMetal);
	}
	
	/**
	 * 
	 * @param unit
	 * @return
	 */
	public int useUnit(Unit unit)
	{
		if (unit.getDef().getName().equals("armcom"))
		{
			buildNextUnit(unit);
		}
		return 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPriority()
	{
		int count = Math.min(_metal.size(), _energy.size());
		if (count < 20)
		{
			return 20 - count;
		}
		else 
		{
			return 1;
		}
	}
	
	/**
	 * 
	 * @param unit
	 */
	protected void buildNextUnit(Unit unit) 
	{
		if (_metal.size() < _energy.size())
		{
			UnitDef unitToBuild = _core.getUnitDefs()._resMetalExtractor;
			AIFloat3 spot = closestMetalSpot(unit.getPos());
			if (spot != null)
			{
				AIFloat3 position = _core.getCallBack().getMap().findClosestBuildSite(unitToBuild, spot, _harvesterRadius, 0, 0);
				AICommand command = new BuildUnitAICommand(unit, -1, new ArrayList<AICommand.Option>(), 10000, unitToBuild, position, 0);
				_core.getCallBack().getEngine().handleCommand(AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
				_core.log("Commander build metal.");
			}
		}
		else
		{
			UnitDef unitToBuild =_core.getUnitDefs()._resSolarPanel;
			AIFloat3 position = _core.getCallBack().getMap().findClosestBuildSite(unitToBuild, unit.getPos(), 1000, 10, 0);
			AICommand command = new BuildUnitAICommand(unit, -1, new ArrayList<AICommand.Option>(), 10000, unitToBuild, position, 0);
			_core.getCallBack().getEngine().handleCommand(AICommandWrapper.COMMAND_TO_ID_ENGINE, -1, command);
			_core.log("Commander build energy.");
		}
	}
	
	/**
	 * 
	 * @param unit
	 */
	public void addMetal(Unit unit) { _metal.add(unit); }
	/**
	 * 
	 * @param unit
	 */
	public void addEnergy(Unit unit) { _energy.add(unit); }
	
	/**
	 * 
	 * @param position
	 * @return
	 */
	protected AIFloat3 closestMetalSpot(AIFloat3 position)
	{
		AIFloat3 finalSpot = null;
		float bestDistanceSquared = 0.0f;
		for (AIFloat3 spot : _metalSpots)
		{
			boolean candidate = true;
			// check if it is free
			for (Unit unit : _metal/*_core.getCallBack().getTeamUnits()*/)
			{
				if (unit.getDef().getName().equals("armmex"))
				{
					//_core.log("Check spot vs armmex:"+ Util.squaredDistance(unit.getPos(), spot) + "," + _harvesterRadius * _harvesterRadius);
					if (Util.squaredDistance(unit.getPos(), spot) < _harvesterRadius * _harvesterRadius)
					{
						candidate = false;
					}
				}
			}
			if (candidate)
			{
				// check if it is near
				float distanceSquared = Util.squaredDistance(position, spot);
				if (finalSpot == null || distanceSquared < bestDistanceSquared)
				{
					finalSpot = spot;
					bestDistanceSquared = distanceSquared;
				}
			}
		}
		return finalSpot;
	}
}
