import hashlib

def hash_password(password):
    return hashlib.md5(password.encode()).hexdigest()

target_password = "sunshine" 
target_hash = hash_password(target_password) 
print(f"Target hash: {target_hash}")  
print()

dictionary = ["123456", "password", "admin", "letmein", "sunshine", "qwerty"]  
flag = False
for word in dictionary:  
    hashed_word = hash_password(word)    
    print(f"hashes: {hashed_word}") 
    if hashed_word == target_hash:  
        print(f"Password cracked: {word}")         
        break 
    else:  
        flag = True

if flag==False:
    print("Password not found in dictionary.") 
