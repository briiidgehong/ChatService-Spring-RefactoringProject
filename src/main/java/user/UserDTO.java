package user;

//자바 빈즈 구축 -> 실제로 데이터베이스에서 뽑아온 한명의 사용자 정보를 이 안에 담을 수 있다.
public class UserDTO {

		String userID;
		String userPassword;
		String userName;
		int userAge;
		String userGender;
		String userEmail;
		String userProfile;
		
		//GETTER 와 SETTER
		//보통 클래스의 멤버변수는 PRIVATE 로 접근제한자를 설정한 후 
		//GETTER, SETTER 를 통해 멤버변수의 값을 변경, 호출하게 된다.
		//SETTER -> 은닉된 멤버 변수에 값을 넣는 방법
		//GETTER -> 은닉된 멤버 변수의 값을 읽는 방법
		
		
		public String getUserID() {
			return userID;
		}
		public void setUserID(String userID) {
			this.userID = userID;
		}
		public String getUserPassword() {
			return userPassword;
		}
		public void setUserPassword(String userPassword) {
			this.userPassword = userPassword;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public int getUserAge() {
			return userAge;
		}
		public void setUserAge(int userAge) {
			this.userAge = userAge;
		}
		public String getUserGender() {
			return userGender;
		}
		public void setUserGender(String userGender) {
			this.userGender = userGender;
		}
		public String getUserEmail() {
			return userEmail;
		}
		public void setUserEmail(String userEmail) {
			this.userEmail = userEmail;
		}
		public String getUserProfile() {
			return userProfile;
		}
		public void setUserProfile(String userProfile) {
			this.userProfile = userProfile;
		}
	
		
		
		
		
}
