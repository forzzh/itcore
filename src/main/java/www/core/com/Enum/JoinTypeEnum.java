package www.core.com.Enum;

public enum JoinTypeEnum implements Enum {

	approve{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "��Ҫ���";
		}
		
	},
	noapprove{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "����Ҫ���";
		}
		
	}
}
