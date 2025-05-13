
const THREE_HOURS_IN_SECONDS: u32 = 60 * 60 * 3;

pub fn variables() {
    println!("three hours in seconds: {}", THREE_HOURS_IN_SECONDS);
    let mut x = 5;
    println!("The value of x is: {}", x);
    x = 6;
    println!("The value of x is: {}", x);
    let x = x + 1;  // 7
    {
        let x = x * 2; // 14
        println!("The value of x in the inner scope is: {}", x);
    }
    println!("The value of x is: {}", x);    // 7
    
    let spaces = "   ";
    let spaces = spaces.len();
}