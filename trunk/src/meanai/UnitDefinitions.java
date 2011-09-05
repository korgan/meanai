package meanai;

import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.UnitDef;

public class UnitDefinitions
{
	public UnitDef _resSolarPanel = null;
	public UnitDef _resMetalExtractor = null;
	
	public UnitDef _kbot1Lab = null;
	public UnitDef _kbot1Contructor = null;
	public UnitDef _kbot1PeeWee = null;

	
	public UnitDefinitions(MeanAI core)
	{
		for (UnitDef def : core.getCallBack().getUnitDefs())
		{
			String name = def.getName();
			if (name.equals("armsolar"))
				_resSolarPanel = def;
			if (name.equals("armmex"))
				_resMetalExtractor = def;
			else if (name.equals("armlab"))
				_kbot1Lab = def;
			else if (name.equals("armck"))
				_kbot1Contructor = def;
			else if (name.equals("armpw"))
				_kbot1PeeWee = def;
		}
	}
}
