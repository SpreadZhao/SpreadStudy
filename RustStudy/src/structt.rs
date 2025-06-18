struct User {
    active: bool,
    username: String,
    email: String,
    sign_in_count: u64,
}

fn user() {
    let mut user = build_user("spreadzhao.outlook.com", "SpreadZhao");
    user.username = String::from("Spread Zhao");
}

fn build_user(email: &str, username: &str) -> User {
    User {
        active: true,
        username: String::from(username),
        email: String::from(email),
        sign_in_count: 1,
    }
}
