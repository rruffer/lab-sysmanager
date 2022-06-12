INSERT INTO 
	Cliente ( dataNasc, telefoneFixo, tipoSanguineo, nome, idade, cpf, rg, signo, mae, pai, email, senha, cep, numero, celular, altura, peso, cor, cidade, estado)
VALUES ( :#${body.dataNasc}, :#${body.telefoneFixo}, :#${body.tipoSanguineo}, :#${body.nome}, 
         :#${body.idade}, :#${body.cpf}, :#${body.rg}, :#${body.signo}, :#${body.mae}, :#${body.pai}, 
         :#${body.email}, :#${body.senha}, :#${body.cep}, :#${body.numero}, :#${body.celular}, :#${body.altura}, 
         :#${body.peso}, :#${body.cor}, :#${body.cidade}, :#${body.estado});