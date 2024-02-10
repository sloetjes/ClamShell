fn main() {    
    use gilrs::{Gilrs, Event};
    
    let mut gilrs = Gilrs::new().unwrap();    
    loop {
        while let Some(Event { id, event, time }) = gilrs.next_event() {
            println!("{}:{:?}", id, event);
        }
    }
}
