
-- Criar essas triggers e functions só na base da nuvem








CREATE OR REPLACE FUNCTION public.alterou_campo (
  anterior pg_catalog.anyelement,
  novo pg_catalog.anyelement
)
RETURNS boolean AS
$body$
DECLARE
   v_aux boolean := false;
   
BEGIN
   if ((anterior is null) and (novo is not null)) then
      v_aux := true;
   elseif ((anterior is not null) and (novo is null)) then
      v_aux := true;
   elseif ((anterior is not null) and (novo is not null) and (anterior <> novo)) then
      v_aux := true;
   end if;

   RETURN v_aux;
END;
$body$
LANGUAGE 'plpgsql'
IMMUTABLE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;

COMMENT ON FUNCTION public.alterou_campo(anterior pg_catalog.anyelement, novo pg_catalog.anyelement)
IS 'Retorna true se houve qualquer alteração no campo quanto ao seu valor anterior e o novo valor';

ALTER FUNCTION public.alterou_campo (anterior pg_catalog.anyelement, novo pg_catalog.anyelement)
  OWNER TO jmkids;


  
  
  
  
  
  
  
  
  
  
CREATE OR REPLACE FUNCTION public.valores_iguais (
  campo1 pg_catalog.anyelement,
  campo2 pg_catalog.anyelement
)
RETURNS boolean AS
$body$
DECLARE
   v_aux boolean := false;
   
BEGIN
   if ((campo1 is null) and (campo2 is null)) then
      v_aux := true;
   elseif ((campo1 is not null) and (campo2 is null)) then
      v_aux := false;
   elseif ((campo1 is null) and (campo2 is not null)) then
      v_aux := false;   
   elseif ((campo1 is not null) and (campo2 is not null) and (campo1 = campo2)) then
      v_aux := true;
   else
      v_aux := false;   
   end if;

   RETURN v_aux;
END;
$body$
LANGUAGE 'plpgsql'
IMMUTABLE
CALLED ON NULL INPUT
SECURITY INVOKER
COST 100;

COMMENT ON FUNCTION public.valores_iguais(campo1 pg_catalog.anyelement, campo2 pg_catalog.anyelement)
IS 'Retorna true se os campos possuem o mesmo conteúdo ou ambos são null. Se não retorna false.';

ALTER FUNCTION public.valores_iguais (campo1 pg_catalog.anyelement, campo2 pg_catalog.anyelement)
  OWNER TO jmkids;  
  
  
  
  
  
  