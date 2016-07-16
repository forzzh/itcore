package www.core.com.Enum;

public enum UserRelationshipEnum implements Enum {

	follow {
		@Override
		public String show() {
			return "¹Ø×¢";
		}
	},
	defollow {
		@Override
		public String show() {
			return "Î´¹Ø×¢";
		}
	}
}
