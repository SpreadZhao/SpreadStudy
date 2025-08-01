pub fn slice() {
    let a = "aa";
    let mut s = String::from("hello world");
    let word = first_word(&s);
    println!("the first word is: {word}");
    first_word("abdc");
    s.clear();
}

fn first_word(s: &str) -> &str {
    let bytes = s.as_bytes();
    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[0..i];
        }
    }
    &s[..]
}
