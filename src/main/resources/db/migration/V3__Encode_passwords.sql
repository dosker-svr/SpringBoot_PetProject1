create extension if not exists pgcrypto;

-- gen_salt('bf', 8) - доп. значение, присоединяемое при шифровании к паролю (для усложнения вкрытия/подбора пароля)
update usr set password = crypt(password, gen_salt('bf', 8));