select * 
  from Cliente 
 where (estado = :#${header.estado} OR :#${header.estado} is null)
   and (cidade = :#${header.cidade} OR :#${header.cidade} is null)
    