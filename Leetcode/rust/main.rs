mod add_binary;
use add_binary::Solution;

fn main() {
    println!("{}", Solution::add_binary("100".to_string(), "10010".to_string()));
}