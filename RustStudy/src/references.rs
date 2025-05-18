
pub fn reference() {
    // let s1 = String::from("hello");
    // let r = &s1;
    // let len = calculate_len(r);
    // println!("The length of '{s1}' is {len}, ref is {}", r);
    let mut s = String::from("hello");
    change(&mut s);
    println!("{}", s);
    println!("len: {}", s.len());
    
    // error
    // let r1 = &mut s;
    // let r2 = &mut s;
    // println!("{}, {}", r1, r2);
    
    
}

fn calculate_len(s: &String) -> usize {
    return s.len();
}

fn change(str: &mut String) {
    str.push_str(", world!");
}
