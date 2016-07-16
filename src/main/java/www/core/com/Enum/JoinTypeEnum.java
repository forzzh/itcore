package www.core.com.Enum;

public enum JoinTypeEnum implements Enum {

	approve{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "需要审核";
		}
		
	},
	noapprove{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "不需要审核";
		}
		
	}
}
