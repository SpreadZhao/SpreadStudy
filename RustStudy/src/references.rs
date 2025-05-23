
pub fn reference() {
    // let s1 = String::from("hello");
    // let r = &s1;
    // let len = calculate_len(r);
    // println!("The length of '{s1}' is {len}, ref is {}", r);
    let mut s = String::from("hello");
    change(&mut s);
    println!("{}", s);
    println!("len: {}", s.len());

    let r1 = &mut s;
    println!("{}", r1);
    let r2 = &mut s;
    println!("{}", r2);
    // println!("{}, {}", r1, r2);
    
    let r1 = &s;
    let r2 = &s;
    println!("{}, {}", r1, r2);
    let r3 = &mut s;
    // println!("{}, {}", r1, r2);
    println!("{}", r3);

}

fn first_word(s: &String) -> &str {
    let bytes = s.as_bytes();
    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return &s[0..i];
        }
    }
    &s[..]
}

// fn dangle() -> &String {
//     let s = String::from("hello");
//     let r = &s;
//     r
// }

fn calculate_len(s: &String) -> usize {
    return s.len();
}

fn change(str: &mut String) {
    str.push_str(", world!");
}
