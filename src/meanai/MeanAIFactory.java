package meanai;

import meanai.MeanAI;

import com.springrts.ai.oo.OOAI;
import com.springrts.ai.oo.OOAICallback;
import com.springrts.ai.oo.OOAIFactory;

public class MeanAIFactory extends OOAIFactory {

	@Override
	public OOAI createAI(int team, OOAICallback callback) 
	{
		// TODO Auto-generated method stub
		return new MeanAI();
	}

}
