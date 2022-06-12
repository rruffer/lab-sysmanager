select * 
  from Cliente 
 where (estado = :#${header.estado} OR :#${header.estado} is null)
   and (cidade = :#${header.cidade} OR :#${header.cidade} is null)
   and (idade >= :#${header.minIdade} OR :#${header.minIdade} is null)
   and (idade <= :#${header.maxIdade} OR :#${header.maxIdade} is null)
 order by idade
    