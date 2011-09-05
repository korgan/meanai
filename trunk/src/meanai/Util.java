package meanai;

import com.springrts.ai.AIFloat3;

public class Util
{

	public static float squaredDistance(AIFloat3 p1, AIFloat3 p2)
	{
		return (p1.x - p2.x) * (p1.x - p2.x) +
				(p1.z - p2.z) * (p1.z - p2.z);
	}
}
