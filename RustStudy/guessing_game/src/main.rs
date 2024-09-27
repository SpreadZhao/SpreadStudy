use std::{cmp::Ordering, io::{self, Write}};
use rand::Rng;

fn main() {
    println!("Guess the number!");

    let secret_number = rand::thread_rng().gen_range(1..=100);
    // println!("The secret number is: {secret_number}");

    let mut error = false;
    loop {
        if error {
            println!("Please type a number!")
        }
        print!("Please input your guess:");
        io::stdout().flush().unwrap();  // flush print!
        
        let mut guess = String::new();
        
        io::stdin()
            .read_line(&mut guess)
            .expect("Failed to read line");

        if guess.trim() == "quit" {
            println!("Bye.");
            break;
        }
    
        let guess: u32 = match guess.trim().parse() {
            Ok(num) => {
                error = false;
                num
            }
            Err(_) => {
                error = true;
                continue;
            }
        };
    
        println!("You guessed: {}", guess);
        
        match guess.cmp(&secret_number) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => {
                println!("You win!");
                break;
            }
        }
    }
}
