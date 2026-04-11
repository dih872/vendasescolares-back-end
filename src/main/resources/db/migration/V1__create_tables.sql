-- Tabela de usuários (APENAS ADMIN)
drop table if exists usuarios cascade ;
CREATE TABLE IF NOT EXISTS usuarios (
id SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
senha VARCHAR(255) NOT NULL,
tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('ADMIN')),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de produtos
drop  table  if exists produtos cascade ;
CREATE TABLE IF NOT EXISTS produtos (
id SERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
descricao TEXT,
preco DECIMAL(10,2) NOT NULL,
imagem VARCHAR(500),
disponivel BOOLEAN DEFAULT true,
categoria VARCHAR(50),
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de feedbacks
drop table if exists feedbacks cascade ;
CREATE TABLE IF NOT EXISTS feedbacks (
id SERIAL PRIMARY KEY,
usuario_id INTEGER REFERENCES usuarios(id) ON DELETE SET NULL,
produto_id INTEGER REFERENCES produtos(id) ON DELETE CASCADE,
nota INTEGER CHECK (nota >= 1 AND nota <= 5),
comentario TEXT,
data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Inserir usuário admin
INSERT INTO usuarios (nome, email, senha, tipo, created_at)
VALUES (
           'Diego Santana Estevao',
           'ds1368833@gmail.com',
           '$2a$10$OfTOflIM0iAAJxORRwfsKeV1otBQuxHiaum3WOmITzdjNZ.LCIE8q',
           'ADMIN',
           NOW()
       ) ON CONFLICT (email) DO NOTHING;

INSERT INTO usuarios (nome, email, senha, tipo, created_at)
VALUES (
           'Ana Vitoria de Araujo',
           'Anav72833@gmail.com',
           '$2a$10$fAl3mChiVaSHg8lFyYudCejBzeO/bZ1SDAOfzRgUcwgT4nTfiZmAa',
           'ADMIN',
           NOW()
       ) ON CONFLICT (email) DO NOTHING;

INSERT INTO usuarios (nome, email, senha, tipo, created_at)
VALUES (
           'Laura Barbosa Clemente',
           'laurabarbosacl332@gmail.com',
           '$2a$10$gpJRJ/3N40mwGn2pCTA5JeFx6KnfpO6ucKLLi5mryVWkLHOtbHP/G',
           'ADMIN',
           NOW()
       ) ON CONFLICT (email) DO NOTHING;

-- Inserir produtos de exemplo
INSERT INTO produtos (nome, descricao, preco, imagem, disponivel, categoria) VALUES
('Coxinha', 'Salgado frito com frango', 5.00, 'https://via.placeholder.com/200', true, 'Salgados'),
('Sanduiche', 'Pão, queijo e presunto', 6.00, 'https://via.placeholder.com/200', true, 'Lanches'),
('Suco', 'Suco de laranja com morango', 4.00, 'https://via.placeholder.com/200', true, 'Bebidas'),
('Bolo de Chocolate', 'Fatia generosa de bolo', 4.50, 'https://via.placeholder.com/200', true, 'Doces')
ON CONFLICT DO NOTHING;

UPDATE usuarios SET senha = '$2a$10$OfTOflIM0iAAJxORRwfsKeV1otBQuxHiaum3WOmITzdjNZ.LCIE8q' WHERE email = 'ds1368833@gmail.com';

UPDATE usuarios SET senha = '$2a$10$fAl3mChiVaSHg8lFyYudCejBzeO/bZ1SDAOfzRgUcwgT4nTfiZmAa' WHERE email = 'Anav72833@gmail.com';

UPDATE usuarios set senha = '$2a$10$gpJRJ/3N40mwGn2pCTA5JeFx6KnfpO6ucKLLi5mryVWkLHOtbHP/G' where email ='laurabarbosacl332@gmail.com'





