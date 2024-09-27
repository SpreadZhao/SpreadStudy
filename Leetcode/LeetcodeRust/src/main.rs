mod solution;
use solution::Solution;

fn main() {
    println!("{}", Solution::add_binary("100".to_string(), "10010".to_string()));
}