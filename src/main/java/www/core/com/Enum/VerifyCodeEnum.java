package www.core.com.Enum;

public enum VerifyCodeEnum implements Enum {
	delete{//ɾ��״̬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "1";
		}
	},
	nodelete{//��ɾ��״̬
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "0";
		}
	},
	use{
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "��ʹ��";
		}
	},
	nouse{	
		@Override
		public String show() {
			// TODO Auto-generated method stub
			return "δʹ��";
		}
	},
}
