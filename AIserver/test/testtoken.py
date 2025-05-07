import jwt
import base64
import dotenv
import os
#Get secret key from .env file
dotenv_path = '.env'
dotenv.load_dotenv(dotenv_path)
secret = os.environ.get("SPRING_JWT_SECRET")

def get_data_from_token(token):
        payload = jwt.decode(token, key=base64.b64decode(secret), algorithms=["HS256"])
        return payload.get("sub")
    
    
x = get_data_from_token('eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTc0NDI4Nzg2NywiZXhwIjoxNzQ0MjkxNDY3fQ.Md6V2EykgqSsfk1RwA4piJqZEjVaicI2oXknBETbq00')
print(x)
