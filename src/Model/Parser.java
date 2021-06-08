package Model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    public static FMModel parse() throws LinhaIncorretaException, NumeroExistenteException, JogadorExistenteException, AtributoInvalidoException, EquipaNaoExistenteException {
        List<String> linhas = lerFicheiro("/home/ray/UM/2ANO/2SEM/OOP/Football_Manager/src/logs.txt");
        Map<String, Equipa> equipas = new HashMap<>(); //nome, equipa
        Map<String, Jogador> jogadores = new HashMap<>(); //numero, jogador
        List<Jogo> jogos = new ArrayList<>();
        Equipa ultima = null; Jogador j = null;
        String[] linhaPartida;

        Boolean addTitular = false;
        int i = 0;
        for (String linha : linhas) {
            linhaPartida = linha.split(":", 2);
            switch(linhaPartida[0]){
                case "Equipa":
                    Equipa e = Equipa.parse(linhaPartida[1]);
                    equipas.put(e.getName(), e);
                    ultima = e;
                    i = 0;
                    break;
                case "Guarda-Redes":
                    j = GuardaRedes.parse(linhaPartida[1]);
                    jogadores.put(j.getNome(), j);
                    if (i < 11) addTitular = true;
                    else addTitular = false;
                    i ++;
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.addPlayer(j.clone(), addTitular); //if no team was parsed previously, file is not well-formed
                    break;
                case "Defesa":
                    j = Defesa.parse(linhaPartida[1]);
                    jogadores.put(j.getNome(), j);
                    if (i < 11) addTitular = true;
                    else addTitular = false;
                    i ++;
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.addPlayer(j.clone(), addTitular); //if no team was parsed previously, file is not well-formed
                    break;
                case "Medio":
                    j = Medio.parse(linhaPartida[1]);
                    jogadores.put(j.getNome(), j);
                    if (i < 11) addTitular = true;
                    else addTitular = false;
                    i ++;
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.addPlayer(j.clone(), addTitular); //if no team was parsed previously, file is not well-formed
                    break;
                case "Lateral":
                    j = Lateral.parse(linhaPartida[1]);
                    jogadores.put(j.getNome(), j);
                    if (i < 11) addTitular = true;
                    else addTitular = false;
                    i ++;
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.addPlayer(j.clone(), addTitular); //if no team was parsed previously, file is not well-formed
                    break;
                case "Avancado":
                    j = Avancado.parse(linhaPartida[1]);
                    jogadores.put(j.getNome(), j);
                    if (i < 11) addTitular = true;
                    else addTitular = false;
                    i ++;
                    if (ultima == null) throw new LinhaIncorretaException(); //we need to insert the player into the team
                    ultima.addPlayer(j.clone(), addTitular); //if no team was parsed previously, file is not well-formed
                    break;
                case "Jogo":
                    Map<String, Equipa> tmp = (HashMap<String, Equipa>) equipas.values().stream().collect(Collectors.toMap(x->x.getName(),x->x.clone()));
                    Jogo jo = Jogo.parse(linhaPartida[1], tmp);
                    i = 0;
                    //jogos.addJogoPredefined();
                    break;
                default:
                    throw new LinhaIncorretaException();
            }
        }
        
        return new FMModel(jogadores, equipas, jogos);
    }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }
}
