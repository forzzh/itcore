package www.core.com.Enum;

public enum VerifyCodeTypeEnum implements Enum {

	findpassword {
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "�һ���������";
		}
	},
	activeUser {
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "�����û�";
		}
	}
}
